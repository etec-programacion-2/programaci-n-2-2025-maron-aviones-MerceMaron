/* Instanciar: Instanciar una clase en otra clase significa crear un objeto (instancia) de una clase dentro de otra clase. Esto permite que una clase utilice las funcionalidades de otra clase.

Inyección de Dependencias manual: La inyección de dependencias manual es un patrón de diseño en el que las dependencias de una clase se proporcionan explícitamente a través de su constructor o mediante métodos de configuración, en lugar de que la clase las cree por sí misma. Esto facilita la gestión de dependencias y mejora la testabilidad del código.

Orquestación de la aplicación: La orquestación de la aplicación se refiere a la coordinación y gestión del flujo de trabajo entre diferentes componentes o módulos de una aplicación para lograr un objetivo específico. Esto implica la integración de servicios, la gestión de estados y la comunicación entre diferentes partes del sistema. */

package org.example
import javafx.application.Application

fun main() {
    println("Seleccione el modo de ejecución:\n1. Interfaz de línea de comandos (CLI)\n2. Interfaz gráfica (GUI)")
    
    val opcion= readln().trim()

    if (opcion == "1") {
        println("\n--- Modo CLI seleccionado ---\n")
        ejecutarCLI()
    } else {
        println("\n--- Modo GUI seleccionado ---\n")
        ejecutarGUI()
    }

}

fun ejecutarCLI() {
    val repositorio = RepositorioMotorEnMemoria() //Maneja el almacenamiento y recuperación de datos de motores
    val servicio = ServicioMantenimientoImpl(repositorio)  //Contiene las reglas de negocio para el mantenimiento de motores
    val vista = VistaConsola //Maneja la interacción con el usuario (es un object singleton - un patrón de diseño que garantiza que una clase tenga solo una instancia en toda la aplicación y provee un punto de acceso global a ella)
    
    vista.mostrarBienvenida() //Mostrar mensaje de bienvenida al usuario
    
    val modelo = vista.solicitarModeloMotor()
    val horas = vista.solicitarHorasVuelo()
   
    val tareas = servicio.calcularTareasPendientes(modelo, horas) //Calcular tareas pendientes
    
    vista.mostrarResultados(tareas)    //Mostrar resultados al usuario
}

fun ejecutarGUI() {
    Application.launch(AppJavaFX::class.java) //se lanza la aplicación JavaFX, mostrando la interfaz gráfica definida en la clase AppJavaFX.
}
