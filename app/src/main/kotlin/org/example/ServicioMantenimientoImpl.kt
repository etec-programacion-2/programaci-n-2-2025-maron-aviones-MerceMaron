/*

 */
package org.example

/* private val hace que la dependencia sea una propiedad privada e inmutable de la clase. 
Recibe como parámetro del constructor una dependencia repositorioMotor de tipo RepositorioMotor. 
El tipo RepositorioMotor (después de los dos puntos) indica que debe ser una instancia de esa interfaz. 
Esto es inyección de dependencias: la clase no crea el repositorio internamente, sino que lo recibe desde afuera */

class ServicioMantenimientoImpl(private val repositorioMotor: RepositorioMotor) : ServicioMantenimiento {
    
    override fun calcularTareasPendientes(modeloMotor: String, horasVueloActuales: Int): List<TareaMantenimiento> {
        
        /* Obtener el motor del repositorio
        repositorioMotor.obtenerPorModelo(): Llama al método obtenerPorModelo del repositorio inyectado.
        modeloMotor: Pasa como argumento el string recibido en el método ("B737", "A320", "E190")
        ?: es el operador Elvis: si el resultado es null, lanza una excepción. */

        val motor = repositorioMotor.obtenerPorModelo(modeloMotor) 
            ?: throw IllegalArgumentException("El modelo de motor '$modeloMotor' no existe")
             

        /* Filtrar las tareas cuyo intervalo sea un divisor exacto de las horas actuales
        Calcula si las horas de vuelo actuales son un múltiplo exacto del intervalo de la tarea. 
        El operador % sirve para obtener el resto de la división, lo que permite saber si es momento de hacer la tarea. 
        Ej: un motor tiene 200 horas y una tarea se debe hacer cada 100 horas, 200 % 100 = 0, por lo que la tarea está pendiente. */

        return motor.planMantenimiento.filter { tarea -> 
            horasVueloActuales % tarea.horasIntervalo == 0 
            
        }
    }
}
