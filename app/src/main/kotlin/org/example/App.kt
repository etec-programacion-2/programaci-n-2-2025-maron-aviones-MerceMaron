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
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ChoiceBox
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class App : Application() {
    override fun start(primaryStage: Stage) {
        val label = Label("DATOS DEL AVION")
        label.setTranslateX(-180.0)
        label.setTranslateY(-180.0)
        val root = StackPane(label)
        val scene = Scene(root, 500.0, 400.0)
        primaryStage.title = "Mantenimineto de aviones"

        //solucionar errores con los textfields
        //TextField inputHorasVueloActuales = new TextField();
        //Int horasVueloActuales = textField.getText();

        //solucioar errores con el choicebox
        val modeloMotor = ChoiceBox<String>()
        modeloMotor.items.addAll("Motor A", "Motor B", "Motor C")
        modeloMotor.setTranslateX(-70.0)
        modeloMotor.setTranslateY(-150.0)
        root.children.add(modeloMotor)

        val labelModeloMotor = Label("Modelo de motor:")
        labelModeloMotor.setTranslateX(-180.0)
        labelModeloMotor.setTranslateY(-150.0)
        root.children.add(labelModeloMotor)

        //posicionar correctamente el botón
        val button = Button("Calcular")
        button.onAction = EventHandler<ActionEvent> {
            label.text = "¡Botón presionado!" //en vez de poner esto, se debe desplegar una TableView con las tareas de mantenimiento a realizar
        }
        root.children.add(button)

        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(App::class.java)
}

