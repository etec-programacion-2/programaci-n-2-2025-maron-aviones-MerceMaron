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

class ServicioMantenimientoImpl(private val repositorioMotor: RepositorioMotor) : ServicioMantenimiento {
    /* 
    private val hace que la dependencia sea una propiedad privada e inmutable de la clase. 
    Recibe como parámetro del constructor una dependencia repositorioMotor de tipo RepositorioMotor. 
    El tipo RepositorioMotor (después de los dos puntos) indica que debe ser una instancia de esa interfaz. 
    Esto es inyección de dependencias: la clase no crea el repositorio internamente, sino que lo recibe desde afuera
    */

    override fun calcularTareasPendientes(modeloMotor: String, horasVueloActuales: Int): List<TareaMantenimiento> {
        // Obtener el motor del repositorio
        val motor = repositorioMotor.obtenerPorModelo(modeloMotor) 
            ?: throw IllegalArgumentException("El modelo de motor '$modeloMotor' no existe")
        
        /*
        repositorioMotor.obtenerPorModelo(): Llama al método obtenerPorModelo del repositorio inyectado.
        modeloMotor: Pasa como argumento el string recibido en el método ("B737", "A320", "E190")
        ?: es el operador Elvis: si el resultado es null, lanza una excepción
        */

        // Filtrar las tareas cuyo intervalo sea un divisor exacto de las horas actuales
        return motor.planMantenimiento.filter { tarea -> 
            horasVueloActuales % tarea.horasIntervalo == 0 
            /*
            Calcula si las horas de vuelo actuales son un múltiplo exacto del intervalo de la tarea. 
            El operador % sirve para obtener el resto de la división, lo que permite saber si es momento de hacer la tarea. 
            Ej: un motor tiene 200 horas y una tarea se debe hacer cada 100 horas, 200 % 100 = 0, por lo que la tarea está pendiente.
            */
        }
    }
}
