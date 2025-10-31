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


//la clase AppJavaFX es la aplicación principal, que hereda de javafx.application.Application. Le permite configurar y ejecutar la interfaz gráfica

class AppJavaFX : Application() {
    
    //se usan dependencias manuales. El controlador MantenimientoController maneja la lógica del programa y se le pasa un servicio (ServicioMantenimientoImpl) y un repositorio de datos (RepositorioMotorEnMemoria).

    private lateinit var controller: MantenimientoController //lateinit se usa para definir una inicialización de una variable de tipo no nulo, que se inicializará más adelante. Asegura que la variable se inicializará antes de su uso.

    //se inicializa más adelante en la construcción de la interfaz porque requieren datos que se proporcionarán más adelante. ChoiceBox con modelos disponibles, TextField con horas de vuelo, Label para mostrar resultados.
    private lateinit var choiceBoxModelo: ChoiceBox<String> 
    private lateinit var textFieldHoras: TextField
    private lateinit var lblResultado: Label
    

    //punto de entrada principal de la aplicación, en donde se configura la ventana principal, que se llama primaryStage

    override fun start(primaryStage: Stage) {
        // Inicializar dependencias (Inyección de Dependencias manual)
        val repositorio = RepositorioMotorEnMemoria()
        val servicio = ServicioMantenimientoImpl(repositorio)
        
        // Crear el controlador con las dependencias
        controller = MantenimientoController(servicio, repositorio)
        
        // Construir la interfaz gráfica
        val root = construirInterfaz(primaryStage)
        
        //establecer título y tamaño de la ventana principal
        val scene = Scene(root, 600.0, 450.0)
        primaryStage.title = "Sistema de Mantenimiento de Aviones - Mercedes Marón"
        primaryStage.scene = scene //la interfaz creada (dentro de scene) se asigna a la ventana principal (primaryStage)
        primaryStage.show() //se muestra la ventana en pantalla
    }


    //crea todos los componentes visuales de la interfaz, usando varias funciones auxiliares que crean controles individuales

    //se crea el método privado construirInterfaz que toma como parámetro la ventana principal y devuelve un objeto VBox, que es un contender vertical para organizar los elementos de la interfaz gráfica.

    private fun construirInterfaz(primaryStage: Stage): VBox {
        val root = VBox(15.0) //se crea un contenedor vertical con un espacio de 15 píxeles entre los elementos
        root.alignment = Pos.CENTER //se centra el contenido dentro del contenedor
        root.padding = Insets(20.0) //se agrega un margen interno de 20 píxeles alrededor del contenedor
        
        // Se crean los componentes de la interfaz
        val titulo = crearTitulo()
        val infoLabel = crearInfoLabel()
        val modeloContainer = crearSelectorModelo()
        val horasContainer = crearCampoHoras()
        val botonesContainer = crearBotones(primaryStage)
        lblResultado = crearLabelResultado()
        

        //se agregan todos los elementos creados previamente al contenedor principal root (del tipo VBox)
        root.children.addAll(
            titulo,
            infoLabel,
            Separator(), //separador visual entre los componentes. No se crea una variable porque no se necesita manipularlo después.
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
        val container = HBox(10.0) //se crea un contenedor horizontal, que organiza los elementos en una fila, uno al lado del otro, con un espacio de 10 píxeles entre los elementos
        container.alignment = Pos.CENTER //se centra el contenido dentro del contenedor
        
        val label = Label("Modelo de motor:")
        label.minWidth = 120.0
        
        choiceBoxModelo = ChoiceBox() //se crea el ChoiceBox desplegable para los modelos de motor
        // Obtener modelos del controlador (que los obtiene del repositorio)
        val modelos = controller.obtenerModelosDisponibles() //se obtiene una lista de modelos de motor disponibles que se guarda en la variable modelos.
        choiceBoxModelo.items.addAll(modelos) //se añaden todos los modelos obtenidos al ChoiceBox
        choiceBoxModelo.setPrefWidth(200.0)
        
        container.children.addAll(label, choiceBoxModelo) //se agregan la etiqueta y el ChoiceBox al contenedor horizontal
        return container //el HBox que contiene el Label y el ChoiceBox se retorna. Esto permite que este contenedor sea agregado como parte de la interfaz gráfica en otra parte del código.
    }
    
    private fun crearCampoHoras(): HBox {
        val container = HBox(10.0)
        container.alignment = Pos.CENTER
        
        val label = Label("Horas de vuelo:")
        label.minWidth = 120.0
        
        textFieldHoras = TextField()
        textFieldHoras.promptText = "Ingrese las horas de vuelo"
        textFieldHoras.setPrefWidth(200.0)
        
        // sólo permite ingresar números y elimina automáticamente cualquier símbolo o letra que se ingrese
        textFieldHoras.textProperty().addListener { _, _, newValue -> //Cada vez que el usuario cambia el texto en el campo de texto, el programa "escucha" ese cambio. La variable newValue contiene el texto nuevo que el usuario acaba de escribir.

        //El código verifica que el texto ingresado no esté vació y si contiene algo que no es un número. Su el texto no está vacío y contiene letras o símbolos, entonces se elimina todo lo que no es un número.
            if (newValue.isNotEmpty() && !newValue.matches(Regex("\\d*"))) {
                textFieldHoras.text = newValue.replace(Regex("\\D"), "") //Regex("\\D") significa "cualquier cosa que no sea un número". replace(..., "") reemplaza esas "cosas no numéricas" por nada (es decir, las elimina).
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

        // EventHandler es una interfaz en JavaFX que define un método llamado handle(). Este método es el que se ejecuta cuando ocurre un evento (por ejemplo, un clic en un botón). Básicamente, el EventHandler escucha el evento y, cuando se dispara (en este caso, al hacer clic en el botón), ejecuta una acción definida dentro de su bloque de código.

        /* onAction es un propiedad del botón que se dispara cuando el usuario hace clic 
           sobre el botón. EventHandler es un manejador de eventos que define qué hacer cuando 
           ocurre el usuario hace clic en el botón. En este caso, se llama a la función 
           handleCalcularTareas, pasando la ventana principal primaryStage como argumento. 
           Esto significa que cuando el usuario haga clic en el botón "Calcular Tareas", 
           se mostrará la ventana emergente con los resultados de las tareas pendientes.
           */
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

   
    private fun crearLabelResultado(): Label { //se crea un Label vacío que se usará para mostrar resultados o mensajes al usuario, en este caso, muestra las tareas a realizar despupes de calcular las tareas pendientes.
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
    
    //Muestra ventana con resultados de tareas de mantenimiento pendientes.
    
    private fun mostrarVentanaResultados(
        //El dueño de la ventana emergente (popup), generalmente es la ventana principal de la aplicación.

        owner: Stage, //El término owner hace referencia a la ventana principal o a la ventana que posee o controla a otra ventana, en este caso, la ventana emergente (popup) es "poseída" por la ventana principal de la aplicación.
        modelo: String, //modelo de motor seleccionado
        horas: Int, //horas de vuelo ingresadas
        tareas: List<TareaMantenimiento>
    ) {
        val popup = Stage()
        popup.title = "Tareas de Mantenimiento - $modelo"
        popup.initModality(Modality.WINDOW_MODAL) //Hace que la ventana emergente sea modal, lo que significa que bloquea la interacción con la ventana principal mientras está abierta (el usuario no puede interactuar con la ventana principal hasta que cierre el popup).
        popup.initOwner(owner) //Establece la ventana principal (owner) como la ventana propietaria del popup. Esto asegura que el popup esté vinculado a la ventana principal y se comporte adecuadamente en términos de comportamiento y cierre.
        
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
    
    // Crea la tabla de resultados para las tareas de mantenimiento
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
