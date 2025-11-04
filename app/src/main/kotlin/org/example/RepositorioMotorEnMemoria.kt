package org.example

// Se hereada la interfaz RepositorioMotor
class RepositorioMotorEnMemoria: RepositorioMotor {

    //se crea una lista de motores privada con sus respectivos planes de mantenimiento
    private val motores = listOf(
        Motor(
            modelo = "A320",
            fabricante = "CFM International",
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.CAMBIO_ACEITE, 10, "Sustituir el aceite del motor"),
                TareaMantenimiento(TipoTarea.CAMBIO_FILTRO, 20, "Reemplazar el filtro de aceite"),
                TareaMantenimiento(TipoTarea.INSPECCION_GENERAL, 50, "Inspección completa del motor")
            )
        ),
        Motor(
            modelo = "B737",
            fabricante = "Pratt & Whitney",
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.CAMBIO_FILTRO, 7, "Limpiar o reemplazar los filtros de aire"),
                TareaMantenimiento(TipoTarea.INSPECCION_BUJIAS, 15, "Revisar el estado de las bujías"),
                TareaMantenimiento(TipoTarea.INSPECCION_GENERAL, 30, "Inspección general del sistema")
            )
        ),
        Motor(
            modelo = "E190",
            fabricante = "General Electric",
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.CAMBIO_ACEITE, 12, "Cambio de aceite y lubricantes"),
                TareaMantenimiento(TipoTarea.REEMPLAZO_MAGNETOS, 25, "Reemplazar magnetos"),
                TareaMantenimiento(TipoTarea.INSPECCION_GENERAL, 20, "Revisión detallada de todos los sistemas y componentes para identificar daños ocultos, evaluar desgastes y cumplir con las normas de seguridad y aeronavegabilidad")
            )
        ),
        Motor(
            modelo = "PA-11",
            fabricante = "Continental",
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.REEMPLAZO_MAGNETOS, 3, "Reemplazar magnetos"),
                TareaMantenimiento(TipoTarea.CAMBIO_FILTRO, 14, "Limpiar o reemplazar los filtros de aire")
            )
        ),
        Motor(
            modelo = "DC-3",
            fabricante = "Curtiss-Wright",
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.REEMPLAZO_MAGNETOS, 4, "Reemplazar magnetos"),
                TareaMantenimiento(TipoTarea.CAMBIO_FILTRO, 29, "Limpiar o reemplazar los filtros de aire"),
                TareaMantenimiento(TipoTarea.CAMBIO_ACEITE, 5, "Sustituir el aceite del motor"),
                TareaMantenimiento(TipoTarea.INSPECCION_BUJIAS, 3, "Revisar el estado de las bujías"),
                TareaMantenimiento(TipoTarea.INSPECCION_GENERAL, 12, "Revisión detallada de todos los sistemas y componentes para identificar daños ocultos, evaluar desgastes y cumplir con las normas de seguridad y aeronavegabilidad")
            )
        ),
        Motor(
            modelo = "ERCO",
            fabricante = "Continental",
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.INSPECCION_GENERAL, 33, "Revisión detallada de todos los sistemas y componentes para identificar daños ocultos, evaluar desgastes y cumplir con las normas de seguridad y aeronavegabilidad")
            )
        ),
        Motor(
            modelo = "C-130",
            fabricante = "Rolls-Royce",
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.REEMPLAZO_MAGNETOS, 30, "Reemplazar magnetos"),
                TareaMantenimiento(TipoTarea.CAMBIO_FILTRO, 20, "Limpiar o reemplazar los filtros de aire"),
                TareaMantenimiento(TipoTarea.CAMBIO_ACEITE, 10, "Sustituir el aceite del motor"),
                TareaMantenimiento(TipoTarea.INSPECCION_BUJIAS, 3, "Revisar el estado de las bujías"),
                TareaMantenimiento(TipoTarea.INSPECCION_GENERAL, 5, "Revisión detallada de todos los sistemas y componentes para identificar daños ocultos, evaluar desgastes y cumplir con las normas de seguridad y aeronavegabilidad")
            )
        ),
        Motor(
            modelo = "F-18",
            fabricante = "General Electric",
            planMantenimiento = listOf(
                TareaMantenimiento(TipoTarea.REEMPLAZO_MAGNETOS, 3, "Reemplazar magnetos"),
                TareaMantenimiento(TipoTarea.CAMBIO_ACEITE, 10, "Sustituir el aceite del motor"),
                TareaMantenimiento(TipoTarea.INSPECCION_BUJIAS, 15, "Revisar el estado de las bujías"),
                TareaMantenimiento(TipoTarea.INSPECCION_GENERAL, 20, "Revisión detallada de todos los sistemas y componentes para identificar daños ocultos, evaluar desgastes y cumplir con las normas de seguridad y aeronavegabilidad")
            )
        )
        
    )

    //se implementa el metodo obtenerPorModelo de la interfaz RepositorioMotor para encontrar un motor por su modelo
    override fun obtenerPorModelo(modelo: String): Motor? {
        return motores.find { it.modelo == modelo }
    }

    //se implementa el metodo obtenerTodos de la interfaz RepositorioMotor para obtener todos los motores
    override fun obtenerTodos(): List<Motor> {
        return motores
    }
}
