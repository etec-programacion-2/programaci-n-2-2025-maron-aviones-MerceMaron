Nombre: Mercedes Marón
Escuela: Escuela Técnica de la Universidad de Mendoza
Año: 4° Informática

------------------------------------------ VERSIONES -------------------------------------------

Versión de Gradle: 9.0.0
Versión de Java: 21
Versión de JavaFX: 20

----------------------------- PASOS PARA QUE LA APLICACIÓN FUNCIONE -----------------------------

Paso 1 - Ubicarse en la carpeta en donde deseas clonar el repositorio
cd (nombre carpeta)

Paso 2 - Clonar el repositorio:
git clone git@github.com:etec-programacion-2/programaci-n-2-2025-maron-aviones-MerceMaron.git

Paso 3 - Ir a la carpeta del repositorio:
cd programaci-n-2-2025-maron-aviones-MerceMaron/

Paso 4 - Ejecutar el código:
./gradlew run

----------------------------------- FUNCIÓN DEL PROGRAMA ------------------------------------

El programa calcula las tareas de mantenimiento que se deben hacer a los motores de aviones (previamente cargados). 

El usuario busca su modelo de motor e ingresa las horas de vuelo. El programa calcula las tareas de mantenimiento a realizar. 

Dentro de la interfaz gráfica, el botón Ver Historial sirve para conocer los modelos que se han buscado recientemente y las tareas que se le realizaron. El botón Ver Motores sirve para conocer los motores cargados en el sistema y su plan de mantenimiento.

Nota: los nombres de los motores se han modificado cambiándolos por el modelo del avión para que su lectura, escritura e interpretación sea más sencilla y clara.


---------------------------------- FUNCIÓN DE CADA CLASE ------------------------------------

App
Es la clase principal que se encarga de ejecutar la función main, en donde se pregunta al usuario si desea ejecutar el programa por consola de comandos o con interfaz gráfica. En función de la respuesta, se ejecuta vistaConsola o AppJavaFX.

TipoTarea
Define los diferentes tipos de mantenimiento que se realizará a los motores.

Motor
Representa un motor de avión con su modelo, fabricante y plan de mantenimiento 

TareMantenimiento
Contiene la información de las tareas de mantenimiento de los motores, considerando las propiedades de tipo, horasIntervalo, descripción

RepositorioMotor
Se encarga de obtener todos los motores

RepositorioMotorEnMemoria
Se crea una lista en memoria con algunos modelos de motores, fabricantes y los detalles de sus tareas de mantenimiento. Cabe aclarar que los parámetros de los motores son representativos y simplificados con el fin de obtener un código más sencillo y fácil de hacer.

ServicioMantenimiento
Calcula las tareas de mantenimiento a partir del modelo del motor y las horas de vuelo.

ServicioMantenimientoImpl
Calcula si las horas de vuelo actuales son un múltiplo exacto del intervalo de la tarea.

vistaConsola
Permite la ejecución del programa mediante consola de comandos.

AppJavaFX
Crea la interfaz gráfica de la aplicación.

MantenimientoController
Maneja las interacciones del usuario con la interfaz, validando los datos ingresados y delegando la lógica de negocio al servicio adecuado (en este caso, el servicio de mantenimiento). La clase MantenimientoController sigue el patrón MVC (Modelo-Vista-Controlador), separando la lógica de presentación (vista) de la lógica de negocio.

build.gradle.kts
Este archivo fue modificado para que JavaFX pudiera ejecutarse. El archivo reconoce como mainClass a App.

