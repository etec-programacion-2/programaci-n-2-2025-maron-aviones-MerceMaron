/* data class: clase especial diseñada para almacenar datos de forma concisa y con menos código repetitivo. Su propósito principal es ser un contenedor de información, en lugar de un objeto con comportamiento complejo. */

//se define la clase TareaMantenimiento con tres propiedades: tipo, horasIntervalo y descripcion.

package org.example

data class TareaMantenimiento(val tipo: TipoTarea, val horasIntervalo: Int, val descripcion: String)
