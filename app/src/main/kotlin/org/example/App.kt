package org.example

import javafx.application.Application
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
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

// ===== INTEGRACIÓN DE TUS CLASES EXISTENTES =====

// Enum TipoTarea (de tu clase TipoTarea.kt)
enum class TipoTarea(val descripcion: String) {
    CAMBIO_ACEITE("Cambio de aceite"),
    CAMBIO_FILTRO("Cambio de filtro"), 
    INSPECCION_BUJIAS("Inspección de bujías"),
    REEMPLAZO_MAGNETOS("Reemplazo de magnetos"),
    INSPECCION_GENERAL("Inspección general")
}

// Data class TareaMantenimiento (adaptada de tu TareaMantenimiento.kt)
data class TareaMantenimiento(
    val tipo: TipoTarea,
    val horasIntervalo: Int,
    val descripcion: String
) {
    // Constructor adicional para compatibilidad con TableView
    constructor(tipoStr: String, horasStr: String, descripcionStr: String, estadoStr: String) :
        this(
            TipoTarea.values().find { it.descripcion == tipoStr } ?: TipoTarea.INSPECCION_GENERAL,
            horasStr.toIntOrNull() ?: 0,
            descripcionStr
        )
}

// Clase Motor (de tu Motor.kt)
class Motor(
    val modelo: String,
    val fabricante: String,
    val planMantenimiento: List<TareaMantenimiento>
) {
    var horasVueloActuales: Int = 0
        private set
    
    fun actualizarHorasVuelo(nuevasHoras: Int) {
        horasVueloActuales = nuevasHoras
    }
    
    fun obtenerTareasPendientes(): List<TareaMantenimiento> {
        return planMantenimiento.filter { tarea ->
            horasVueloActuales >= tarea.horasIntervalo
        }
    }
}

// Interface RepositorioMotor (de tu RepositorioMotor.kt)
interface RepositorioMotor {
    fun obtenerPorModelo(modelo: String): Motor?
    fun obtenerTodos(): List<Motor>
}

// Implementación RepositorioMotorEnMemoria (de tu RepositorioMotorEnMemoria.kt)
class RepositorioMotorEnMemoria : RepositorioMotor {
    private val motores = listOf(
        Motor(
            modelo = "A320",
            fabricante = "CFM International",
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.CAMBIO_ACEITE, 25, "Cambio de aceite del motor"),
                TareaMantenimiento(TipoTarea.INSPECCION_BUJIAS, 50, "Inspección y limpieza de bujías"),
                TareaMantenimiento(TipoTarea.CAMBIO_FILTRO, 100, "Cambio de filtro de aire"),
                TareaMantenimiento(TipoTarea.INSPECCION_GENERAL, 500, "Inspección general completa")
            )
        ),
        Motor(
            modelo = "B737",
            fabricante = "Pratt & Whitney", 
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.CAMBIO_ACEITE, 30, "Cambio de aceite del motor"),
                TareaMantenimiento(TipoTarea.CAMBIO_FILTRO, 75, "Cambio de filtros de combustible"),
                TareaMantenimiento(TipoTarea.INSPECCION_BUJIAS, 150, "Inspección detallada de bujías"),
                TareaMantenimiento(TipoTarea.REEMPLAZO_MAGNETOS, 300, "Reemplazo de magnetos")
            )
        ),
        Motor(
            modelo = "E190",
            fabricante = "General Electric",
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.INSPECCION_GENERAL, 40, "Inspección general básica"),
                TareaMantenimiento(TipoTarea.CAMBIO_ACEITE, 80, "Cambio completo de aceite"),
                TareaMantenimiento(TipoTarea.CAMBIO_FILTRO, 200, "Cambio de todos los filtros")
            )
        )
    )

    override fun obtenerPorModelo(modelo: String): Motor? {
        return motores.find { it.modelo == modelo }
    }

    override fun obtenerTodos(): List<Motor> {
        return motores
    }
}

// Interface ServicioMantenimiento (de tu ServicioMantenimiento.kt)
interface ServicioMantenimiento {
    fun calcularTareasPendientes(modeloMotor: String, horasVueloActuales: Int): List<TareaMantenimiento>
}

// Implementación ServicioMantenimientoImpl (de tu ServicioMantenimientoImpl.kt)
class ServicioMantenimientoImpl(private val repositorioMotor: RepositorioMotor) : ServicioMantenimiento {
    
    override fun calcularTareasPendientes(modeloMotor: String, horasVueloActuales: Int): List<TareaMantenimiento> {
        val motor = repositorioMotor.obtenerPorModelo(modeloMotor)
            ?: throw IllegalArgumentException("El modelo de motor no existe")
        
        motor.actualizarHorasVuelo(horasVueloActuales)
        
        return motor.planMantenimiento.filter { tarea ->
            horasVueloActuales % tarea.horasIntervalo == 0
        }
    }
}

// Data class para mostrar en TableView
data class TareaParaTabla(
    val tipoProperty: SimpleStringProperty,
    val descripcionProperty: SimpleStringProperty,
    val horasProperty: SimpleIntegerProperty,
    val estadoProperty: SimpleStringProperty
) {
    constructor(tarea: TareaMantenimiento, estado: String = "Pendiente") : this(
        SimpleStringProperty(tarea.tipo.descripcion),
        SimpleStringProperty(tarea.descripcion),
        SimpleIntegerProperty(tarea.horasIntervalo),
        SimpleStringProperty(estado)
    )
    
    val tipo: String get() = tipoProperty.get()
    val descripcion: String get() = descripcionProperty.get()  
    val horas: Int get() = horasProperty.get()
    val estado: String get() = estadoProperty.get()
}

// ===== INTERFAZ GRÁFICA JAVAFX =====
class AppJavaFX : Application() {
    
    private val repositorio = RepositorioMotorEnMemoria()
    private val servicio = ServicioMantenimientoImpl(repositorio)
    private val historialTareas = mutableListOf<TareaParaTabla>()
    
    override fun start(primaryStage: Stage) {
        // Layout principal
        val root = VBox(15.0)
        root.alignment = Pos.CENTER
        root.padding = Insets(20.0)
        
        // Título
        val titulo = Label("SISTEMA DE MANTENIMIENTO DE AVIONES")
        titulo.style = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;"
        
        // Información del sistema
        val infoLabel = Label("Seleccione el modelo de motor e ingrese las horas de vuelo")
        infoLabel.style = "-fx-font-size: 12px; -fx-text-fill: #7f8c8d;"
        
        // Contenedor para modelo de motor
        val modeloContainer = HBox(10.0)
        modeloContainer.alignment = Pos.CENTER
        val labelModelo = Label("Modelo de motor:")
        labelModelo.minWidth = 120.0
        
        val choiceBoxModelo = ChoiceBox<String>()
        val modelosDisponibles = repositorio.obtenerTodos().map { it.modelo }
        choiceBoxModelo.items.addAll(modelosDisponibles)
        choiceBoxModelo.setPrefWidth(200.0)
        modeloContainer.children.addAll(labelModelo, choiceBoxModelo)
        
        // Contenedor para horas de vuelo
        val horasContainer = HBox(10.0)
        horasContainer.alignment = Pos.CENTER
        val labelHoras = Label("Horas de vuelo:")
        labelHoras.minWidth = 120.0
        
        val textFieldHoras = TextField()
        textFieldHoras.promptText = "Ingrese las horas de vuelo"
        textFieldHoras.setPrefWidth(200.0)
        horasContainer.children.addAll(labelHoras, textFieldHoras)
        
        // Contenedor de botones
        val botonesContainer = HBox(15.0)
        botonesContainer.alignment = Pos.CENTER
        
        // Botón Calcular
        val btnCalcular = Button("Calcular Tareas")
        btnCalcular.setPrefWidth(130.0)
        btnCalcular.style = "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;"
        
        // Botón Ver Historial
        val btnHistorial = Button("Ver Historial")
        btnHistorial.setPrefWidth(130.0)
        btnHistorial.style = "-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;"
        
        // Botón Ver Todos los Motores
        val btnMotores = Button("Ver Motores")
        btnMotores.setPrefWidth(130.0)
        btnMotores.style = "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;"
        
        botonesContainer.children.addAll(btnCalcular, btnHistorial, btnMotores)
        
        // Label para mostrar resultado
        val lblResultado = Label("")
        lblResultado.style = "-fx-font-size: 12px; -fx-text-fill: #e74c3c;"
        
        // ===== EVENTOS DE BOTONES =====
        
        btnCalcular.onAction = EventHandler<ActionEvent> {
            try {
                val modeloSeleccionado = choiceBoxModelo.value
                val horasTexto = textFieldHoras.text
                
                if (modeloSeleccionado == null || horasTexto.isBlank()) {
                    lblResultado.text = "Por favor complete todos los campos"
                    lblResultado.style = "-fx-text-fill: #e74c3c;"
                    return@EventHandler
                }
                
                val horas = horasTexto.toIntOrNull()
                if (horas == null || horas < 0) {
                    lblResultado.text = "Las horas de vuelo deben ser un número válido"
                    lblResultado.style = "-fx-text-fill: #e74c3c;"
                    return@EventHandler
                }
                
                val tareasPendientes = servicio.calcularTareasPendientes(modeloSeleccionado, horas)
                
                if (tareasPendientes.isEmpty()) {
                    lblResultado.text = "No hay tareas de mantenimiento pendientes para $modeloSeleccionado con $horas horas"
                    lblResultado.style = "-fx-text-fill: #27ae60;"
                    mostrarVentanaResultados(primaryStage, modeloSeleccionado, horas, emptyList())
                } else {
                    lblResultado.text = "Se encontraron ${tareasPendientes.size} tareas pendientes"
                    lblResultado.style = "-fx-text-fill: #f39c12;"
                    mostrarVentanaResultados(primaryStage, modeloSeleccionado, horas, tareasPendientes)
                }
                
            } catch (e: Exception) {
                lblResultado.text = "Error: ${e.message}"
                lblResultado.style = "-fx-text-fill: #e74c3c;"
            }
        }
        
        btnHistorial.onAction = EventHandler<ActionEvent> {
            mostrarVentanaHistorial(primaryStage)
        }
        
        btnMotores.onAction = EventHandler<ActionEvent> {
            mostrarVentanaMotores(primaryStage)
        }
        
        // Agregar elementos al layout principal
        root.children.addAll(
            titulo,
            infoLabel,
            Separator(),
            modeloContainer,
            horasContainer,
            botonesContainer,
            lblResultado
        )
        
        val scene = Scene(root, 600.0, 450.0)
        primaryStage.title = "Sistema de Mantenimiento de Aviones - Mercedes Marón"
        primaryStage.scene = scene
        primaryStage.show()
    }
    
    // Ventana emergente para mostrar resultados
    private fun mostrarVentanaResultados(owner: Stage, modelo: String, horas: Int, tareas: List<TareaMantenimiento>) {
        val popup = Stage()
        popup.title = "Tareas de Mantenimiento - $modelo"
        popup.initModality(Modality.WINDOW_MODAL)
        popup.initOwner(owner)
        
        val tableView = TableView<TareaParaTabla>()
        
        // Definir columnas
        val colTipo = TableColumn<TareaParaTabla, String>("Tipo de Tarea")
        colTipo.cellValueFactory = PropertyValueFactory("tipo")
        colTipo.prefWidth = 150.0
        
        val colDescripcion = TableColumn<TareaParaTabla, String>("Descripción")
        colDescripcion.cellValueFactory = PropertyValueFactory("descripcion")
        colDescripcion.prefWidth = 250.0
        
        val colHoras = TableColumn<TareaParaTabla, Number>("Intervalo (horas)")
        colHoras.cellValueFactory = PropertyValueFactory("horas")
        colHoras.prefWidth = 120.0
        
        val colEstado = TableColumn<TareaParaTabla, String>("Estado")
        colEstado.cellValueFactory = PropertyValueFactory("estado")
        colEstado.prefWidth = 100.0
        
        tableView.columns.addAll(colTipo, colDescripcion, colHoras, colEstado)
        
        // Agregar datos
        val tareasParaTabla = tareas.map { TareaParaTabla(it, "Requerida") }
        tableView.items.addAll(tareasParaTabla)
        
        // Si se ejecutaron tareas, agregarlas al historial
        historialTareas.addAll(tareasParaTabla.map { 
            TareaParaTabla(
                SimpleStringProperty(it.tipo),
                SimpleStringProperty("${it.descripcion} - $modelo"),
                SimpleIntegerProperty(horas),
                SimpleStringProperty("Completada")
            )
        })
        
        val popupRoot = VBox(10.0)
        popupRoot.padding = Insets(15.0)
        
        val tituloPopup = Label("Mantenimiento requerido para $modelo ($horas horas de vuelo)")
        tituloPopup.style = "-fx-font-size: 14px; -fx-font-weight: bold;"
        
        val cerrarBtn = Button("Cerrar")
        cerrarBtn.onAction = EventHandler { popup.close() }
        
        popupRoot.children.addAll(tituloPopup, tableView, cerrarBtn)
        
        val popupScene = Scene(popupRoot, 650.0, 400.0)
        popup.scene = popupScene
        popup.show()
    }
    
    // Ventana para mostrar historial
    private fun mostrarVentanaHistorial(owner: Stage) {
        val popup = Stage()
        popup.title = "Historial de Mantenimiento"
        popup.initModality(Modality.WINDOW_MODAL)
        popup.initOwner(owner)
        
        val tableView = TableView<TareaParaTabla>()
        
        val colTarea = TableColumn<TareaParaTabla, String>("Tarea")
        colTarea.cellValueFactory = PropertyValueFactory("descripcion")
        colTarea.prefWidth = 300.0
        
        val colHoras = TableColumn<TareaParaTabla, Number>("Horas de Vuelo")
        colHoras.cellValueFactory = PropertyValueFactory("horas")
        colHoras.prefWidth = 120.0
        
        val colEstado = TableColumn<TareaParaTabla, String>("Estado")
        colEstado.cellValueFactory = PropertyValueFactory("estado")
        colEstado.prefWidth = 100.0
        
        tableView.columns.addAll(colTarea, colHoras, colEstado)
        tableView.items.addAll(historialTareas)
        
        val popupRoot = VBox(10.0)
        popupRoot.padding = Insets(15.0)
        
        val titulo = Label("Historial de Tareas Realizadas")
        titulo.style = "-fx-font-size: 14px; -fx-font-weight: bold;"
        
        val cerrarBtn = Button("Cerrar")
        cerrarBtn.onAction = EventHandler { popup.close() }
        
        popupRoot.children.addAll(titulo, tableView, cerrarBtn)
        
        val popupScene = Scene(popupRoot, 550.0, 400.0)
        popup.scene = popupScene
        popup.show()
    }
    
    // Ventana para mostrar todos los motores
    private fun mostrarVentanaMotores(owner: Stage) {
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
        
        val colTareas = TableColumn<Motor, String>("Tareas de Mantenimiento")
        colTareas.setCellValueFactory { cellData ->
            val motor = cellData.value
            val numeroTareas = motor.planMantenimiento.size
            SimpleStringProperty("$numeroTareas tareas definidas")
        }
        colTareas.prefWidth = 180.0
        
        tableView.columns.addAll(colModelo, colFabricante, colTareas)
        tableView.items.addAll(repositorio.obtenerTodos())
        
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

// Punto de entrada principal
fun main() {
    Application.launch(AppJavaFX::class.java)
}