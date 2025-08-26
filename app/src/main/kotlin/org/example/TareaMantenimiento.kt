/*
    Título: Crear una data class para representar una tarea de mantenimiento específica.
    Descripción: Cada tarea de mantenimiento no solo tiene un tipo, sino también detalles asociados como las horas de vuelo a las que debe realizarse. Esta clase encapsulará toda esa información.
    Objetivo de Aprendizaje: Modelado de datos con data class y encapsulamiento de propiedades.
    Prerrequisitos: Issue 1.1.
    Criterios de Aceptación:
        Debe existir una data class llamada TareaMantenimiento.
        Debe contener las propiedades: tipo (del tipo TipoTarea), horasIntervalo (Int), y descripcion (String).
    Estimación de Esfuerzo: 1 sesión. */

data class TareaMantenimiento(val tipo: TipoTarea, val horasIntervalo: Int, val descripción: String)