/*
    Título: Crear un enum class para los tipos de tareas de mantenimiento.
    Descripción: Se necesita una forma estandarizada y segura de representar los diferentes tipos de acciones de mantenimiento que se pueden realizar, como cambios de fluidos, reemplazo de componentes o inspecciones.
    Objetivo de Aprendizaje: Uso de enum class para representar un conjunto finito de constantes, promoviendo la seguridad de tipos.
    Prerrequisitos: Ninguno.
    Criterios de Aceptación:
        Debe existir un enum class llamado TipoTarea.
        Debe contener al menos los siguientes valores: CAMBIO_ACEITE, CAMBIO_FILTRO, INSPECCION_BUJIAS, REEMPLAZO_MAGNETOS, INSPECCION_GENERAL.
    Estimación de Esfuerzo: 1 sesión.
 */

package org.example

 enum class TipoTarea {CAMBIO_ACEITE, CAMBIO_FILTRO, INSPECCION_BUJIAS, REEMPLAZO_MAGNETOS, INSPECCION_GENERAL}

 