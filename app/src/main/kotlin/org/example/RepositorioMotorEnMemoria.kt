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
            planMantenimiento = TareaMantenimiento("Cambio de aceite", 10, "Sustituir el aceite del motor")
            )
        ),
        Motor(
            modelo = "B737",
            fabricante = "Pratt & Whitney",
            planMantenimiento = TareaMantenimiento("Cambio de filtros", 7, "Limpiar o reemplazar los filtros de aire")
        ),
        Motor(
            modelo = "E190",
            fabricante = "General Electric",
            planMantenimiento = TareaMantenimiento("Inspección general", 20, "Revisión detallada de todos los sistemas y componentes para identificar daños ocultos, evaluar desgastes y cumplir con las normas de seguridad y aeronavegabilidad")          
        )
    )

    override fun obtenerPorModelo(modelo: String): Motor? {
        return motores.find { it.modelo == modelo }
    }

    override fun obtenerTodos(): List<Motor> {
        return motores
    }
}