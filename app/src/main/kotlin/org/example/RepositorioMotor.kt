/* 
Interface: es un contrato que define un conjunto de métodos (y a veces propiedades) que una
clase debe implementar, especificando "qué puede hacer" una clase pero no "cómo lo hace". 
*/

package org.example

interface RepositorioMotor {

    /*
    se obtiene el motor por su modelo. 
    Motor? indica que puede devolver un objeto Motor o null si no se encuentra.
    */
    fun obtenerPorModelo(modelo: String): Motor?

    //se obtienen todos los motores disponibles en el repositorio.
    fun obtenerTodos(): List<Motor>
}