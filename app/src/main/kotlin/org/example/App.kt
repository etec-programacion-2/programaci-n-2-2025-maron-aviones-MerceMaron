/* Instanciar: Instanciar una clase en otra clase significa crear un objeto (instancia) de una 
clase dentro de otra clase. Esto permite que una clase utilice las funcionalidades de otra clase.

Inyección de Dependencias manual: La inyección de dependencias manual es un patrón de diseño en 
el que las dependencias de una clase se proporcionan explícitamente a través de su constructor o 
mediante métodos de configuración, en lugar de que la clase las cree por sí misma. Esto facilita 
la gestión de dependencias y mejora la testabilidad del código.

Orquestación de la aplicación: La orquestación de la aplicación se refiere a la coordinación y 
gestión del flujo de trabajo entre diferentes componentes o módulos de una aplicación para 
lograr un objetivo específico. Esto implica la integración de servicios, la gestión de estados y 
la comunicación entre diferentes partes del sistema. */

package org.example
import javafx.application.Application

class App {
    fun iniciar() {
        println("Seleccione el modo de ejecución:")
        println("1. Interfaz de línea de comandos (CLI)")
        println("2. Interfaz gráfica (GUI)")
        
        val opcion = readln().trim()
        
        when (opcion) {
            "1" -> {
                println("\n--- Modo CLI seleccionado ---\n")
                ejecutarCLI()
            }
            "2" -> {
                println("\n--- Modo GUI seleccionado ---\n")
                ejecutarGUI()
            }
            else -> {
                println("\n--- Modo GUI seleccionado por defecto ---\n")
                ejecutarGUI()
            }
        }
    }
    
    private fun ejecutarCLI() {
        val repositorio = RepositorioMotorEnMemoria()
        val servicio = ServicioMantenimientoImpl(repositorio)
        val vista = VistaConsola
        
        vista.mostrarBienvenida()
        val modelo = vista.solicitarModeloMotor()
        val horas = vista.solicitarHorasVuelo()
        val tareas = servicio.calcularTareasPendientes(modelo, horas)
        vista.mostrarResultados(tareas)
    }
    
    private fun ejecutarGUI() {
        Application.launch(AppJavaFX::class.java)
    }
}

fun main() {
    App().iniciar()
}