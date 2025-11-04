package org.example

class ServicioMantenimientoImpl(private val repositorioMotor: RepositorioMotor) : ServicioMantenimiento {

/* 
private val hace que la dependencia sea una propiedad privada e inmutable de la clase. 
Recibe como parámetro del constructor una dependencia repositorioMotor de tipo RepositorioMotor. 
El tipo RepositorioMotor (después de los dos puntos) indica que debe ser una instancia de esa interfaz. 
Esto es inyección de dependencias: la clase no crea el repositorio internamente, sino que lo recibe desde afuera 
*/
    
    override fun calcularTareasPendientes(modeloMotor: String, horasVueloActuales: Int): List<TareaMantenimiento> {
        
        //Obtener el motor del repositorio
        val motor = repositorioMotor.obtenerPorModelo(modeloMotor) 
            ?: throw IllegalArgumentException("El modelo de motor '$modeloMotor' no existe")
        
        /*
        repositorioMotor.obtenerPorModelo(): Llama al método obtenerPorModelo del repositorio inyectado.
        modeloMotor: Pasa como argumento el string recibido en el método ("B737", "A320", "E190")
        ?: es el operador Elvis: si el resultado es null, lanza una excepción
        */

        return motor.planMantenimiento.filter { tarea -> 
            horasVueloActuales >= tarea.horasIntervalo
            
            /*
            horasVueloActuales >= tarea.horasIntervalo: Verifica que ya se hayan alcanzado las
            horas mínimas requeridas
            */
        }
    }
}
