package com.miapp.controllers

import javafx.fxml.FXML
import javafx.scene.control.*

class MainController {
    
    @FXML
    private lateinit var txtIdMotor: TextField
    
    @FXML
    private lateinit var txtMarca: TextField
    
    @FXML
    private lateinit var btnRegistrar: Button
    
    @FXML
    private lateinit var txtResultados: TextArea
    
    // Aquí irían tus servicios inyectados
    // private val servicio = ServicioMantenimientoImpl(repositorio)
    
    @FXML
    fun initialize() {
        // Configuración inicial de la interfaz
        txtResultados.isEditable = false
    }
    
    @FXML
    fun registrarMotor() {
        val id = txtIdMotor.text
        val marca = txtMarca.text
        
        if (id.isNotBlank() && marca.isNotBlank()) {
            // Aquí usarías tu servicio
            // servicio.registrarMotor(Motor(id, marca))
            
            txtResultados.appendText("Motor registrado: $id - $marca\n")
            limpiarCampos()
        } else {
            mostrarError("Por favor complete todos los campos")
        }
    }
    
    private fun limpiarCampos() {
        txtIdMotor.clear()
        txtMarca.clear()
    }
    
    private fun mostrarError(mensaje: String) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = "Error"
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
}