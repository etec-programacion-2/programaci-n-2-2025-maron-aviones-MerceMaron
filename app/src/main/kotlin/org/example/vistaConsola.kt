/*
    Título: Crear un objeto VistaConsola para la interacción con el usuario.
    Descripción: Para mantener la lógica de la aplicación separada de la presentación, todo el código relacionado con println y readln se encapsulará en un objeto singleton.
    Objetivo de Aprendizaje: Separación de incumbencias (SoC), uso de object para singletons.
    Prerrequisitos: Ninguno.
    Criterios de Aceptación:
        Debe existir un object llamado VistaConsola.
        Tendrá métodos como mostrarBienvenida(), solicitarModeloMotor(): String, solicitarHorasVuelo(): Int, y mostrarResultados(tareas: List<TareaMantenimiento>).
    Estimación de Esfuerzo: 2 sesiones.
 
 Singleton: es una clase que permite tener una única instancia (realización concreta y única de una clase) de sí misma y proporciona un punto de acceso global a esa instancia. En Kotlin, se puede crear un singleton utilizando la palabra clave `object`.
 Se utiliza para garantizar que solo exista un objeto de su tipo en un programa, como en la gestión de configuración o el registro, proporcionando un único punto para acceder a ese recurso. 
 Se logra haciendo que el constructor de la clase sea privado, lo que impide crear nuevas instancias desde fuera. 
 Se proporciona un método estático (o propiedad) a través del cual se puede acceder a la única instancia de la clase. Cada vez que se llama a este método, se devuelve la misma instancia creada previamente
 
 */
package org.example

object VistaConsola {
    fun mostrarBienvenida(){
        println("¡Bienvenido al programa de mantenimiento de aviones!")
    }
    /* 
    fun solicitarModeloMotor(): String{
        println("Por favor, ingrese el modelo del motor: ")
        return readln() //se utiliza para leer una línea completa de texto de entrada y la devuelve como una cadena no nula
    }

    fun solicitarHorasVuelo(): Int{
        println("Por favor, ingrese las horas de vuelo. Tenga en cuenta que debe ser un número entero: ")
        return readln().toInt() //s.toInt() se usa para convertir un string a un número entero
    }*/

    fun mostrarResultados(tareas: List<TareaMantenimiento>){
        println ("Las tareas de mantenimiento que debe hacerle a su avión son: ")
        for (tarea in tareas){
            println("- ${tarea.tipo}: ${tarea.descripcion}. Cada ${tarea.horasIntervalo} horas")
        }
    }
}