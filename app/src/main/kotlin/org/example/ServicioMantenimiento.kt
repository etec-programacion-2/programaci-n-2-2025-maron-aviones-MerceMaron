//interface que calcula las tareas de mantenimiento pendientes para un motor dado a partir de su modelo y horas de vuelo actuales

package org.example

interface ServicioMantenimiento {
    fun calcularTareasPendientes(modeloMotor: String, horasVueloActuales: Int): List<TareaMantenimiento> } 