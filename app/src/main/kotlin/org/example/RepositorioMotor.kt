/*
    Título: Crear la interfaz RepositorioMotor.
    Descripción: Para que nuestra lógica de negocio no dependa de una fuente de datos concreta (ej: una lista en memoria, un archivo JSON, una base de datos), definiremos un contrato (interfaz) que cualquier implementación de repositorio deberá cumplir.
    Objetivo de Aprendizaje: Principio de Inversión de Dependencias (SOLID), Polimorfismo a través de interfaces.
    Prerrequisitos: Issue 1.3.
    Criterios de Aceptación:
        Debe existir una interface llamada RepositorioMotor.
        Debe definir métodos como obtenerPorModelo(modelo: String): Motor? y obtenerTodos(): List<Motor>.
    Estimación de Esfuerzo: 2 sesiones.

 */

package org.example

interface RepositorioMotor {
    fun obtenerPorModelo(modelo: String): Motor?
    fun obtenerTodos(): List<Motor>
}