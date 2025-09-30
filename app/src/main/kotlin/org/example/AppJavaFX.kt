package org.example

import javafx.application.Application
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import org.example.controllers.MantenimientoController
import org.example.controllers.TareaParaTabla

/**
Título: Diseñar y construir la ventana principal de la aplicación con JavaFX.
Descripción: Se creará la interfaz gráfica donde el usuario podrá introducir los datos del motor y ver los resultados.
Objetivo de Aprendizaje: Fundamentos de JavaFX (Layouts, Controles) y separación Vista-Controlador.
Prerrequisitos: Issue 4.2.
Criterios de Aceptación:
    Debe existir una ventana principal con campos de texto para el modelo y las horas de vuelo.
    Debe haber un botón para Calcular y un área (ej: ListView o TableView) para mostrar las tareas resultantes.
*/

class AppJavaFX : Application() {
    
    // El controlador se inyecta con las dependencias necesarias
    private lateinit var controller: MantenimientoController
    
    // Referencias a componentes de la vista que el controlador necesita modificar
    private lateinit var choiceBoxModelo: ChoiceBox<String>
    private lateinit var textFieldHoras: TextField
    private lateinit var lblResultado: Label
    
    override fun start(primaryStage: Stage) {
        // Inicializar dependencias (Inyección de Dependencias manual)
        val repositorio = RepositorioMotorEnMemoria()
        val servicio = ServicioMantenimientoImpl(repositorio)
        
        // Crear el controlador con las dependencias
        controller = MantenimientoController(servicio, repositorio)
        
        // Construir la interfaz gráfica
        val root = construirInterfaz(primaryStage)
        
        val scene = Scene(root, 600.0, 450.0)
        primaryStage.title = "Sistema de Mantenimiento de Aviones - Mercedes Marón"
        primaryStage.scene = scene
        primaryStage.show()
    }
    
    /**
     * Construye todos los componentes de la interfaz gráfica.
     */
    private fun construirInterfaz(primaryStage: Stage): VBox {
        val root = VBox(15.0)
        root.alignment = Pos.CENTER
        root.padding = Insets(20.0)
        
        // Componentes de la interfaz
        val titulo = crearTitulo()
        val infoLabel = crearInfoLabel()
        val modeloContainer = crearSelectorModelo()
        val horasContainer = crearCampoHoras()
        val botonesContainer = crearBotones(primaryStage)
        lblResultado = crearLabelResultado()
        
        // Ensamblar la interfaz
        root.children.addAll(
            titulo,
            infoLabel,
            Separator(),
            modeloContainer,
            horasContainer,
            botonesContainer,
            lblResultado
        )
        
        return root
    }
    
    private fun crearTitulo(): Label {
        val titulo = Label("SISTEMA DE MANTENIMIENTO DE AVIONES")
        titulo.style = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;"
        return titulo
    }
    
    private fun crearInfoLabel(): Label {
        val infoLabel = Label("Seleccione el modelo de motor e ingrese las horas de vuelo")
        infoLabel.style = "-fx-font-size: 12px; -fx-text-fill: #7f8c8d;"
        return infoLabel
    }
    
    /**
     * Crea el selector de modelo de motor.
     * Los modelos disponibles se obtienen del controlador.
     */
    private fun crearSelectorModelo(): HBox {
        val container = HBox(10.0)
        container.alignment = Pos.CENTER
        
        val label = Label("Modelo de motor:")
        label.minWidth = 120.0
        
        choiceBoxModelo = ChoiceBox()
        // Obtener modelos del controlador (que los obtiene del repositorio)
        val modelos = controller.obtenerModelosDisponibles()
        choiceBoxModelo.items.addAll(modelos)
        choiceBoxModelo.setPrefWidth(200.0)
        
        container.children.addAll(label, choiceBoxModelo)
        return container
    }
    
    private fun crearCampoHoras(): HBox {
        val container = HBox(10.0)
        container.alignment = Pos.CENTER
        
        val label = Label("Horas de vuelo:")
        label.minWidth = 120.0
        
        textFieldHoras = TextField()
        textFieldHoras.promptText = "Ingrese las horas de vuelo"
        textFieldHoras.setPrefWidth(200.0)
        
        // Validación en tiempo real (solo números)
        textFieldHoras.textProperty().addListener { _, _, newValue ->
            if (newValue.isNotEmpty() && !newValue.matches(Regex("\\d*"))) {
                textFieldHoras.text = newValue.replace(Regex("\\D"), "")
            }
        }
        
        container.children.addAll(label, textFieldHoras)
        return container
    }
    
    private fun crearBotones(primaryStage: Stage): HBox {
        val container = HBox(15.0)
        container.alignment = Pos.CENTER
        
        // Botón Calcular - Evento principal que invoca al controlador
        val btnCalcular = Button("Calcular Tareas")
        btnCalcular.setPrefWidth(130.0)
        btnCalcular.style = "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;"
        btnCalcular.onAction = EventHandler { handleCalcularTareas(primaryStage) }
        
        // Botón Ver Historial
        val btnHistorial = Button("Ver Historial")
        btnHistorial.setPrefWidth(130.0)
        btnHistorial.style = "-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;"
        btnHistorial.onAction = EventHandler { handleVerHistorial(primaryStage) }
        
        // Botón Ver Motores
        val btnMotores = Button("Ver Motores")
        btnMotores.setPrefWidth(130.0)
        btnMotores.style = "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;"
        btnMotores.onAction = EventHandler { handleVerMotores(primaryStage) }
        
        container.children.addAll(btnCalcular, btnHistorial, btnMotores)
        return container
    }
    
    private fun crearLabelResultado(): Label {
        val label = Label("")
        label.style = "-fx-font-size: 12px; -fx-text-fill: #e74c3c;"
        return label
    }
    
    // ===== MANEJADORES DE EVENTOS =====
    // Cada manejador delega al controlador
    
    /**
     * Maneja el evento de calcular tareas.
     * DELEGA al controlador para procesar la lógica de negocio.
     */
    private fun handleCalcularTareas(primaryStage: Stage) {
        // El controlador maneja toda la lógica
        val tareasPendientes = controller.calcularTareasPendientes(
            choiceBoxModelo,
            textFieldHoras,
            lblResultado
        )
        
        // Si hay tareas, mostrar ventana emergente
        if (tareasPendientes != null) {
            val modelo = choiceBoxModelo.value
            val horas = textFieldHoras.text.toIntOrNull() ?: 0
            mostrarVentanaResultados(primaryStage, modelo, horas, tareasPendientes)
        }
    }
    
    /**
     * Maneja el evento de ver historial.
     */
    private fun handleVerHistorial(primaryStage: Stage) {
        val historial = controller.obtenerHistorial()
        
        if (historial.isEmpty()) {
            controller.mostrarAlertaInfo(
                "Historial vacío",
                "Aún no se han calculado tareas. Use el botón 'Calcular Tareas' primero."
            )
            return
        }
        
        mostrarVentanaHistorial(primaryStage, historial)
    }
    
    /**
     * Maneja el evento de ver motores disponibles.
     */
    private fun handleVerMotores(primaryStage: Stage) {
        val motores = controller.obtenerTodosLosMotores()
        mostrarVentanaMotores(primaryStage, motores)
    }
    
    // ===== VENTANAS EMERGENTES =====
    
    /**
     * Muestra ventana con resultados de tareas pendientes.
     */
    private fun mostrarVentanaResultados(
        owner: Stage,
        modelo: String,
        horas: Int,
        tareas: List<TareaMantenimiento>
    ) {
        val popup = Stage()
        popup.title = "Tareas de Mantenimiento - $modelo"
        popup.initModality(Modality.WINDOW_MODAL)
        popup.initOwner(owner)
        
        val tableView = crearTablaResultados(tareas)
        
        val popupRoot = VBox(10.0)
        popupRoot.padding = Insets(15.0)
        
        val tituloPopup = Label("Mantenimiento requerido para $modelo ($horas horas de vuelo)")
        tituloPopup.style = "-fx-font-size: 14px; -fx-font-weight: bold;"
        
        val infoLabel = Label("Las siguientes tareas deben realizarse:")
        infoLabel.style = "-fx-font-size: 11px; -fx-text-fill: #7f8c8d;"
        
        val cerrarBtn = Button("Cerrar")
        cerrarBtn.onAction = EventHandler { popup.close() }
        
        popupRoot.children.addAll(tituloPopup, infoLabel, tableView, cerrarBtn)
        
        val popupScene = Scene(popupRoot, 650.0, 400.0)
        popup.scene = popupScene
        popup.show()
    }
    
    private fun crearTablaResultados(tareas: List<TareaMantenimiento>): TableView<TareaParaTabla> {
        val tableView = TableView<TareaParaTabla>()
        
        val colTipo = TableColumn<TareaParaTabla, String>("Tipo de Tarea")
        colTipo.cellValueFactory = PropertyValueFactory("tipo")
        colTipo.prefWidth = 150.0
        
        val colDescripcion = TableColumn<TareaParaTabla, String>("Descripción")
        colDescripcion.cellValueFactory = PropertyValueFactory("descripcion")
        colDescripcion.prefWidth = 300.0
        
        val colHoras = TableColumn<TareaParaTabla, Number>("Intervalo (horas)")
        colHoras.cellValueFactory = PropertyValueFactory("horas")
        colHoras.prefWidth = 120.0
        
        val colEstado = TableColumn<TareaParaTabla, String>("Estado")
        colEstado.cellValueFactory = PropertyValueFactory("estado")
        colEstado.prefWidth = 100.0
        
        tableView.columns.addAll(colTipo, colDescripcion, colHoras, colEstado)
        
        val tareasParaTabla = tareas.map { TareaParaTabla(it, "Requerida") }
        tableView.items.addAll(tareasParaTabla)
        
        return tableView
    }
    
    /**
     * Muestra ventana con historial de tareas.
     */
    private fun mostrarVentanaHistorial(owner: Stage, historial: List<TareaParaTabla>) {
        val popup = Stage()
        popup.title = "Historial de Mantenimiento"
        popup.initModality(Modality.WINDOW_MODAL)
        popup.initOwner(owner)
        
        val tableView = TableView<TareaParaTabla>()
        
        val colTarea = TableColumn<TareaParaTabla, String>("Tarea Realizada")
        colTarea.cellValueFactory = PropertyValueFactory("descripcion")
        colTarea.prefWidth = 300.0
        
        val colHoras = TableColumn<TareaParaTabla, Number>("Horas de Vuelo")
        colHoras.cellValueFactory = PropertyValueFactory("horas")
        colHoras.prefWidth = 120.0
        
        val colEstado = TableColumn<TareaParaTabla, String>("Estado")
        colEstado.cellValueFactory = PropertyValueFactory("estado")
        colEstado.prefWidth = 100.0
        
        tableView.columns.addAll(colTarea, colHoras, colEstado)
        tableView.items.addAll(historial)
        
        val popupRoot = VBox(10.0)
        popupRoot.padding = Insets(15.0)
        
        val titulo = Label("Historial de Tareas Realizadas (${historial.size} tareas)")
        titulo.style = "-fx-font-size: 14px; -fx-font-weight: bold;"
        
        val cerrarBtn = Button("Cerrar")
        cerrarBtn.onAction = EventHandler { popup.close() }
        
        popupRoot.children.addAll(titulo, tableView, cerrarBtn)
        
        val popupScene = Scene(popupRoot, 550.0, 400.0)
        popup.scene = popupScene
        popup.show()
    }
    
    /**
     * Muestra ventana con todos los motores disponibles.
     */
    private fun mostrarVentanaMotores(owner: Stage, motores: List<Motor>) {
        val popup = Stage()
        popup.title = "Motores Disponibles"
        popup.initModality(Modality.WINDOW_MODAL)
        popup.initOwner(owner)
        
        val tableView = TableView<Motor>()
        
        val colModelo = TableColumn<Motor, String>("Modelo")
        colModelo.cellValueFactory = PropertyValueFactory("modelo")
        colModelo.prefWidth = 150.0
        
        val colFabricante = TableColumn<Motor, String>("Fabricante")
        colFabricante.cellValueFactory = PropertyValueFactory("fabricante")
        colFabricante.prefWidth = 200.0
        
        val colTareas = TableColumn<Motor, String>("Plan de Mantenimiento")
        colTareas.setCellValueFactory { cellData ->
            val motor = cellData.value
            val numeroTareas = motor.planMantenimiento.size
            SimpleStringProperty("$numeroTareas tareas programadas")
        }
        colTareas.prefWidth = 180.0
        
        tableView.columns.addAll(colModelo, colFabricante, colTareas)
        tableView.items.addAll(motores)
        
        val popupRoot = VBox(10.0)
        popupRoot.padding = Insets(15.0)
        
        val titulo = Label("Base de Datos de Motores")
        titulo.style = "-fx-font-size: 14px; -fx-font-weight: bold;"
        
        val cerrarBtn = Button("Cerrar")
        cerrarBtn.onAction = EventHandler { popup.close() }
        
        popupRoot.children.addAll(titulo, tableView, cerrarBtn)
        
        val popupScene = Scene(popupRoot, 550.0, 400.0)
        popup.scene = popupScene
        popup.show()
    }
}

fun main() {
    Application.launch(AppJavaFX::class.java)
}