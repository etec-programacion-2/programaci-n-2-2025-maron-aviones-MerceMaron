package org.example.controllers

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import org.example.*

/**
 * Controlador de la interfaz JavaFX que conecta la vista con la lógica de negocio.
 * 
 * Responsabilidades:
 * - Recibir eventos del usuario desde la vista
 * - Validar datos de entrada
 * - Invocar servicios de negocio
 * - Actualizar la vista con los resultados
 * 
 * Este controlador implementa el patrón MVC separando la lógica de presentación
 * de la lógica de negocio (ServicioMantenimiento).
 */

class MantenimientoController(
    private val servicio: ServicioMantenimiento,
    private val repositorio: RepositorioMotor
) {
    
    // Historial de tareas calculadas (para persistencia en la sesión)
    private val historialTareas = mutableListOf<TareaParaTabla>()
    
    /**
     * Maneja el evento de calcular tareas pendientes.
     * 
     * choiceBoxModelo ComboBox con el modelo seleccionado
     * textFieldHoras Campo de texto con las horas de vuelo
     * lblResultado Label para mostrar mensajes al usuario
     * retorna Lista de tareas pendientes o null si hay error
     */
    
    fun calcularTareasPendientes(
        choiceBoxModelo: ChoiceBox<String>,
        textFieldHoras: TextField,
        lblResultado: Label
    ): List<TareaMantenimiento>? {
        try {
            // 1. Obtener valores de la vista
            val modeloSeleccionado = choiceBoxModelo.value
            val horasTexto = textFieldHoras.text
            
            // 2. Validar entrada
            val validacionResultado = validarEntrada(modeloSeleccionado, horasTexto)
            if (!validacionResultado.esValido) {
                actualizarMensajeError(lblResultado, validacionResultado.mensaje)
                return null
            }
            
            val horas = horasTexto.toInt()
            
            // 3. Invocar lógica de negocio (ServicioMantenimiento)
            val tareasPendientes = servicio.calcularTareasPendientes(modeloSeleccionado!!, horas)
            
            // 4. Actualizar vista con resultado
            if (tareasPendientes.isEmpty()) {
                actualizarMensajeExito(
                    lblResultado,
                    "No hay tareas de mantenimiento pendientes para $modeloSeleccionado con $horas horas"
                )
            } else {
                actualizarMensajeAdvertencia(
                    lblResultado,
                    "Se encontraron ${tareasPendientes.size} tareas pendientes"
                )
                
                // Agregar al historial
                agregarAlHistorial(tareasPendientes, modeloSeleccionado, horas)
            }
            
            return tareasPendientes
            
        } catch (e: IllegalArgumentException) {
            actualizarMensajeError(lblResultado, "Error: ${e.message}")
            return null
        } catch (e: Exception) {
            actualizarMensajeError(lblResultado, "Error inesperado: ${e.message}")
            return null
        }
    }
    
    /**
     * Valida los datos ingresados por el usuario.
     */
    private fun validarEntrada(modelo: String?, horas: String?): ResultadoValidacion {
        if (modelo == null || modelo.isBlank()) {
            return ResultadoValidacion(false, "Por favor seleccione un modelo de motor")
        }
        
        if (horas.isNullOrBlank()) {
            return ResultadoValidacion(false, "Por favor ingrese las horas de vuelo")
        }
        
        val horasNumero = horas.toIntOrNull()
        if (horasNumero == null) {
            return ResultadoValidacion(false, "Las horas de vuelo deben ser un número válido")
        }
        
        if (horasNumero < 0) {
            return ResultadoValidacion(false, "Las horas de vuelo no pueden ser negativas")
        }
        
        return ResultadoValidacion(true, "")
    }
    
    /**
     * Obtiene la lista de modelos disponibles desde el repositorio.
     */
    fun obtenerModelosDisponibles(): List<String> {
        return repositorio.obtenerTodos().map { it.modelo }
    }
    
    /**
     * Obtiene todos los motores del repositorio.
     */
    fun obtenerTodosLosMotores(): List<Motor> {
        return repositorio.obtenerTodos()
    }
    
    /**
     * Obtiene el historial de tareas calculadas en la sesión actual.
     */
    fun obtenerHistorial(): List<TareaParaTabla> {
        return historialTareas.toList()
    }
    
    /**
     * Agrega tareas al historial de la sesión.
     */
    private fun agregarAlHistorial(tareas: List<TareaMantenimiento>, modelo: String, horas: Int) {
        val nuevasTareas = tareas.map { tarea ->
            TareaParaTabla(
                SimpleStringProperty(tarea.tipo.name),
                SimpleStringProperty("${tarea.descripcion} - $modelo"),
                SimpleIntegerProperty(horas),
                SimpleStringProperty("Completada")
            )
        }
        historialTareas.addAll(nuevasTareas)
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
    
    // Métodos privados para actualizar mensajes en la vista
    
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

/**
 * Clase auxiliar para el resultado de validación.
 */
private data class ResultadoValidacion(
    val esValido: Boolean,
    val mensaje: String
)

/**
 * Data class para representar tareas en TableView.
 * Esta clase es específica de la vista y no pertenece al modelo de dominio.
 */
data class TareaParaTabla(
    val tipoProperty: SimpleStringProperty,
    val descripcionProperty: SimpleStringProperty,
    val horasProperty: SimpleIntegerProperty,
    val estadoProperty: SimpleStringProperty
) {
    constructor(tarea: TareaMantenimiento, estado: String = "Pendiente") : this(
        SimpleStringProperty(tarea.tipo.name),
        SimpleStringProperty(tarea.descripcion),
        SimpleIntegerProperty(tarea.horasIntervalo),
        SimpleStringProperty(estado)
    )
    
    val tipo: String get() = tipoProperty.get()
    val descripcion: String get() = descripcionProperty.get()
    val horas: Int get() = horasProperty.get()
    val estado: String get() = estadoProperty.get()
}