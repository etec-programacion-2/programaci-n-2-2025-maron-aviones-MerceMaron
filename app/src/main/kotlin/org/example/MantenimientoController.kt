/*
Controlador de la interfaz JavaFX que conecta la vista con la lógica de negocio.

Responsabilidades:
 - Recibir eventos del usuario desde la vista
 - Validar datos de entrada
 - Invocar servicios de negocio
 - Actualizar la vista con los resultados
Este controlador implementa el patrón MVC separando la lógica de presentación de la lógica
de negocio (ServicioMantenimiento).
*/

package org.example.controllers

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import org.example.*

class MantenimientoController(
    private val servicio: ServicioMantenimiento, //contiene la lógica del mantenimiento
    private val repositorio: RepositorioMotor) { //maneja los motores disponibles
    
    private val historialTareas = mutableListOf<TareaParaTabla>() //lista de tareas para la tabla de historial, en la sesión actual
        
    fun calcularTareasPendientes(
        choiceBoxModelo: ChoiceBox<String>, //recibe el modelo seleccionado
        textFieldHoras: TextField, //recibe las horas de vuelo ingresadas
        lblResultado: Label
    ): List<TareaMantenimiento>? {
        try {
            //se toma el valor del motor (ChoiceBox) y las horas (TextField)
            val modeloSeleccionado = choiceBoxModelo.value
            val horasTexto = textFieldHoras.text
            
            // se valida que los datos ingresados sean correctos (el modelo no sea nulo ni vacío, las horas sean un número válido y positivo). Si la validación falla, se muestra un mensaje de error en lblResultado.
            val validacionResultado = validarEntrada(modeloSeleccionado, horasTexto)
            if (!validacionResultado.esValido) {
                actualizarMensajeError(lblResultado, validacionResultado.mensaje)
                return null
            }
            
            val horas = horasTexto.toInt()
            
            // Calcular tareas pendientes: Si la entrada es válida, se llama a la función calcularTareasPendientes del servicio de mantenimiento (servicio) para obtener las tareas que deben realizarse en función del modelo de motor y las horas de vuelo.
            val tareasPendientes = servicio.calcularTareasPendientes(modeloSeleccionado!!, horas) //Llama al servicio de negocio para calcular qué tareas deben realizarse para ese modelo y número de horas.
            

            // Si no hay tareas pendientes, muestra un mensaje de éxito.

            if (tareasPendientes.isEmpty()) {
                actualizarMensajeExito(
                    lblResultado,
                    "No hay tareas de mantenimiento pendientes para $modeloSeleccionado con $horas horas"
                )
            } else { //si hay tareas pendientes, muestra una advertencia o incluye las tareas en el historial
                actualizarMensajeAdvertencia(
                    lblResultado,
                    "Se encontraron ${tareasPendientes.size} tareas pendientes"
                )
                
                // Agregar al historial
                agregarAlHistorial(tareasPendientes, modeloSeleccionado, horas)
            }
            
            return tareasPendientes
            
        } catch (e: IllegalArgumentException) { //manejo de errores específicos (parámetros inválidos)
            actualizarMensajeError(lblResultado, "Error: ${e.message}")
            return null //se retorna null para indicar que no se pudieron calcular las tareas debido al error.
        } catch (e: Exception) { //manejo de errores generales (cualquier otro error inesperado)
            actualizarMensajeError(lblResultado, "Error inesperado: ${e.message}")
            return null
        }
    }
    
    /**
     * Valida los datos ingresados por el usuario.
     */
    private fun validarEntrada(modelo: String?, horas: String?): ResultadoValidacion {
        if (modelo == null || modelo.isBlank()) { //se valida que el modelo no sea nulo ni vacío
            return ResultadoValidacion(false, "Por favor seleccione un modelo de motor")
        }
        
        if (horas.isNullOrBlank()) { //se valida que las horas no sean nulas ni vacías
            return ResultadoValidacion(false, "Por favor ingrese las horas de vuelo")
        }
        
        val horasNumero = horas.toIntOrNull()
        if (horasNumero == null) { //se valida que las horas sean un número válido
            return ResultadoValidacion(false, "Las horas de vuelo deben ser un número válido")
        }
        
        if (horasNumero < 0) { //se valida que las horas sean positivas
            return ResultadoValidacion(false, "Las horas de vuelo no pueden ser negativas")
        }
        
        return ResultadoValidacion(true, "")
    }
    
    //Obtiene la lista de modelos disponibles desde el repositorio
    fun obtenerModelosDisponibles(): List<String> {
        return repositorio.obtenerTodos().map { it.modelo }
    }
    
    //Obtiene todos los motores del repositorio
    fun obtenerTodosLosMotores(): List<Motor> {
        return repositorio.obtenerTodos()
    }
    
    //Obtiene el historial de tareas calculadas en la sesión actual
    fun obtenerHistorial(): List<TareaParaTabla> {
        return historialTareas.toList()
    }
    
    
    //Convierte cada TareaMantenimiento en un TareaParaTabla (adaptada para la vista)
    private fun agregarAlHistorial(tareas: List<TareaMantenimiento>, modelo: String, horas: Int) {
        val nuevasTareas = tareas.map { tarea ->
            TareaParaTabla(
                SimpleStringProperty(tarea.tipo.name),
                SimpleStringProperty("${tarea.descripcion} - $modelo"),
                SimpleIntegerProperty(horas),
                SimpleStringProperty("Completada")
            )
        }
        historialTareas.addAll(nuevasTareas) //Agrega tareas al historial de la sesión
    }
    
    /**
     * Muestra una alerta de información al usuario.
     */
    fun mostrarAlertaInfo(titulo: String, mensaje: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = titulo
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
    
    /**
     * Muestra una alerta de error al usuario.
     */
    fun mostrarAlertaError(titulo: String, mensaje: String) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = titulo
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
    
    //Cambia el color y el texto del label según el tipo de mensaje (error, éxito, advertencia)
    private fun actualizarMensajeError(label: Label, mensaje: String) {
        label.text = mensaje
        label.style = "-fx-text-fill: #e74c3c; -fx-font-size: 12px;"
    }
    
    private fun actualizarMensajeExito(label: Label, mensaje: String) {
        label.text = mensaje
        label.style = "-fx-text-fill: #27ae60; -fx-font-size: 12px;"
    }
    
    private fun actualizarMensajeAdvertencia(label: Label, mensaje: String) {
        label.text = mensaje
        label.style = "-fx-text-fill: #f39c12; -fx-font-size: 12px;"
    }
}


// Representa el resultado de validar los datos de entrada del usuario.

private data class ResultadoValidacion(
    val esValido: Boolean, //indica si la validación fue exitosa
    val mensaje: String) 

//Data class para mostrar datos en una TableView.
//TableView y TexField o Label pueden vincularse a propiedades observables, lo que permite que la interfaz se actualice automáticamente cuando los datos cambian.

data class TareaParaTabla(
    val tipoProperty: SimpleStringProperty, //SimpleStringProperty representa un texto observable.
    val descripcionProperty: SimpleStringProperty,
    val horasProperty: SimpleIntegerProperty, //SimpleIntegerProperty representa un número entero observable. 
    val estadoProperty: SimpleStringProperty
) {
    constructor(tarea: TareaMantenimiento, estado: String = "Pendiente") : this( //constructor secundario para crear una instancia a partir de TareaMantenimiento
        SimpleStringProperty(tarea.tipo.name), //convierte el tipo de tarea (enum) a String
        SimpleStringProperty(tarea.descripcion),
        SimpleIntegerProperty(tarea.horasIntervalo), 
        SimpleStringProperty(estado) //estado de la tarea (Pendiente o Completada)
    )
    
    val tipo: String get() = tipoProperty.get() //propiedades de solo lectura para acceder a los valores subyacentes
    val descripcion: String get() = descripcionProperty.get()
    val horas: Int get() = horasProperty.get()
    val estado: String get() = estadoProperty.get()
}