/*
    Título: Implementar la función main para orquestar el flujo.
    Descripción: La función main será responsable de armar la aplicación: creará las instancias del repositorio y del servicio (Inyección de Dependencias manual) y las pasará a la vista o controlador principal para iniciar el flujo del programa.
    Objetivo de Aprendizaje: Inyección de Dependencias manual, orquestación de la aplicación.
    Prerrequisitos: Issue 3.2, Issue 4.1.
    Criterios de Aceptación:
        La función main debe estar en un archivo Main.kt.
        Debe instanciar RepositorioMotorEnMemoria y ServicioMantenimientoImpl.
        Debe usar VistaConsola para interactuar con el usuario.
        El programa debe ejecutarse, solicitar datos, calcular y mostrar los resultados de forma cíclica hasta que el usuario decida salir.
    Estimación de Esfuerzo: 2 sesiones.


    Instanciar: Instanciar una clase en otra clase significa crear un objeto (instancia) de una clase dentro de otra clase. Esto permite que una clase utilice las funcionalidades de otra clase.

    Inyección de Dependencias manual: La inyección de dependencias manual es un patrón de diseño en el que las dependencias de una clase se proporcionan explícitamente a través de su constructor o mediante métodos de configuración, en lugar de que la clase las cree por sí misma. Esto facilita la gestión de dependencias y mejora la testabilidad del código.

    Orquestación de la aplicación: La orquestación de la aplicación se refiere a la coordinación y gestión del flujo de trabajo entre diferentes componentes o módulos de una aplicación para lograr un objetivo específico. Esto implica la integración de servicios, la gestión de estados y la comunicación entre diferentes partes del sistema.
 */

package.org.example

fun main() {
    val repositorio = RepositorioMotorEnMemoria() //Maneja el almacenamiento y recuperación de datos de motores. Encapsula operaciones como: guardar, buscar, actualizar, eliminar motores

    val servicio = ServicioMantenimientoImpl(respositorio) //Contiene las reglas de negocio para el mantenimiento de motores. Coordina operaciones complejas usando el repositorio. Valida datos, aplica reglas, orquesta operaciones

    val vista = VistaConsola(servicio) //Maneja la interacción con el usuario. Muestra menús, recibe input, presenta resultados. Traduce las acciones del usuario en llamadas al servicio

    vista.start() //Llama al método que arranca la interfaz de usuario
}