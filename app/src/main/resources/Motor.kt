/*
    Título: Crear una clase para representar un motor y su plan de mantenimiento.
    Descripción: Esta clase será el corazón del modelo de dominio. Representará un tipo de motor específico y contendrá su plan de mantenimiento asociado, es decir, la lista de todas las tareas que requiere.
    Objetivo de Aprendizaje: Composición de objetos y encapsulamiento de una colección.
    Prerrequisitos: Issue 1.2.
    Criterios de Aceptación:
        Debe existir una clase Motor.
        Debe tener propiedades como modelo (String) y fabricante (String).
        Debe contener una propiedad planMantenimiento que sea una lista de TareaMantenimiento.
    Estimación de Esfuerzo: 2 sesiones.

 */

package main.resources

class Motor (val modelo: String, val fabricante: String, val planMantenimiento:List<TareaMantenimiento>){
}