/*
    Título: Diseñar y construir la ventana principal de la aplicación con JavaFX.
    Descripción: Se creará la interfaz gráfica donde el usuario podrá introducir los datos del motor y ver los resultados.
    Objetivo de Aprendizaje: Fundamentos de JavaFX (Layouts, Controles) y separación Vista-Controlador.
    Prerrequisitos: Issue 4.2.
    Criterios de Aceptación:
        Debe existir una ventana principal con campos de texto para el modelo y las horas de vuelo.
        Debe haber un botón para Calcular y un área (ej: ListView o TableView) para mostrar las tareas resultantes.
    Estimación de Esfuerzo: 3 sesiones.

 */
package org.example

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class App : Application() {
    override fun start(primaryStage: Stage) {
        val label = Label("¡Hola desde JavaFX en Linux!")
        val root = StackPane(label)
        val scene = Scene(root, 400.0, 300.0)
        primaryStage.title = "Kotlin + JavaFX"
        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(App::class.java)
}

