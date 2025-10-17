/*
    Título: Crear la interfaz ServicioMantenimiento.
    Descripción: Siguiendo el principio de Inversión de Dependencias, crearemos una interfaz para el servicio que calculará las tareas pendientes. Esto permitirá que las interfaces de usuario (CLI, JavaFX) dependan de esta abstracción y no de una implementación concreta.
    Objetivo de Aprendizaje: Principio de Responsabilidad Única y Principio de Inversión de Dependencias (SOLID).
    Prerrequisitos: Issue 2.1.
    Criterios de Aceptación:
        Debe existir una interface llamada ServicioMantenimiento.
        Debe definir un método principal: calcularTareasPendientes(modeloMotor: String, horasVueloActuales: Int): List<TareaMantenimiento>.
    Estimación de Esfuerzo: 2 sesiones.
 */
 package org.example

 interface ServicioMantenimiento {
    fun calcularTareasPendientes(modeloMotor: String, horasVueloActuales: Int): List<TareaMantenimiento>
 }