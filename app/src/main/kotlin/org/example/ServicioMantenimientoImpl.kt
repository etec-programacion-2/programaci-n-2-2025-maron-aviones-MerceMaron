/*
Título: Crear la clase ServicioMantenimientoImpl.
Descripción: Esta clase contendrá la lógica principal. Usará el RepositorioMotor para obtener los datos de un motor y, basándose en las horas de vuelo, calculará qué tareas de mantenimiento son necesarias.
Objetivo de Aprendizaje: Inyección de Dependencias (a través del constructor), algoritmos y lógica de negocio.
Prerrequisitos: Issue 3.1, Issue 2.2.
Criterios de Aceptación:

    Debe existir una clase ServicioMantenimientoImpl que implemente ServicioMantenimiento.
    Debe recibir una instancia de RepositorioMotor en su constructor.
    El método calcularTareasPendientes debe devolver correctamente las tareas cuyo horasIntervalo es un múltiplo de las horas de vuelo actuales (o una lógica similar que se defina).
    Debe manejar el caso en que el modelo de motor no exista.

Estimación de Esfuerzo: 3 sesiones.
 */
package org.example

class ServicioMantenimientoImpl (private val RepositorioMotor: RepositorioMotor) :  
/* 
private val hace que la dependencia sea una propiedad privada e inmutable de la clase. Recibe como parámetro del constructor una dependencia RepositorioMotor de tipo RepositorioMotor. 
//El tipo RepositorioMotor (después de los dos puntos) indica que debe ser una instancia de esa clase. Esto es inyección de dependencias: la clase no crea el repositorio internamente, sino que lo recibe desde afuera
*/


ServicioMantenimiento { // se hereda de la interfaz ServicioMantenimiento

    override fun calcularTareasPendientes(modeloMotor: String): List<TareaMantenimiento> {
        val motor = RepositorioMotor.obtenerMotor(modeloMotor) 
        /*
        RepositorioMotor.obtenerMotor(): Llama al método obtenerMotor del repositorio inyectado.
        modeloMotor: Pasa como argumento el string recibido en el método ("B737", "A320", "E190")
        Kotlin deduce automáticamente que motor será del tipo que retorne obtenerMotor()
        */

            ?: throw IllegalArgumentException("El modelo de motor no existe") //el ?: se usa para valores nulos. 

        val horasVuelo = motor.horasVueloActuales //Extrae las horas de vuelo actuales del objeto motor encontrado.

        return motor.tareasMantenimiento.filter { tarea -> // Toma la lista de tareas de mantenimiento del motor y aplica un filtro que usa una lambda que recibe cada tarea como parámetro

            horasVuelo % tarea.horasIntervalo == 0 
            /*
            calcula si las horas de vuelo actuales son un múltiplo exacto del intervalo de la tarea. 
            El % sirve para obtener el resto de la división, lo que permite saber si es momento de hacer la tarea. 
            Ej: un motor tiene 200 horas y una tarea se debe hacer cada 100 horas, 200 % 100 = 0, por lo que la tarea está pendiente.
            */
        }
    }
}