/*
    Título: Crear la clase RepositorioMotorEnMemoria.
    Descripción: Se creará una primera implementación falsa o mock del repositorio que guarde los datos de los motores en una lista dentro del código. Esto nos permitirá avanzar con el resto de la aplicación sin preocuparnos todavía por la persistencia en archivos.
    Objetivo de Aprendizaje: Implementación de interfaces, inicialización de datos de prueba.
    Prerrequisitos: Issue 2.1.
    Criterios de Aceptación:
        Debe existir una clase RepositorioMotorEnMemoria que implemente RepositorioMotor.
        La clase debe tener al menos 2-3 motores de ejemplo pre-cargados en una lista privada.
        Los métodos de la interfaz deben operar sobre esa lista.
    Estimación de Esfuerzo: 2 sesiones.
 */
package org.example

class RepositorioMotorEnMemoria: RepositorioMotor {
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
        )
    )

    override fun obtenerPorModelo(modelo: String): Motor? {
        return motores.find { it.modelo == modelo }
    }

    override fun obtenerTodos(): List<Motor> {
        return motores
    }
}