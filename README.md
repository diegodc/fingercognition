# fingercognition
### API REST para Validación de Huellas

Fingercognition es un servicio REST implementado en Java

#### **Tecnologías Utilizadas:**

###### Spring Boot:
Se eligió Spring por las facilidades que brinda para crear servicios REST, ya que requiere de poca configuración y facilita la integración con la base de datos.

###### MongoDb:
Se eligió MongoDb como base de datos ya que tiene una muy buena performance, y para una aplicación de este estilo, ante la necesidad de persistir una gran cantidad de huellas, una base NoSql como MongoDb tiene la ventaja de poder ser escalable horizontalmente.

###### JUnit - Mockito
Utilizadas para los test de unidad. En la carpeta **test-coverage-report** se puede ver el reporte de code coverage.

___

### Comentarios

- El enunciado dice que una matriz de minucias es valida si se encuentran **una o mas secuencias de 5 letras iguales**. Pero en los ejemplos se muestran secuencias de 4 letras iguales. Para esta aplicación, **se consideran validas las matrices con mas de una secuencia de 4 (cuatro) letras iguales.**

- Al procesar una matriz de minucias, la aplicación busca la huella en la base de datos, si la huella no existe, se procede entonces a validar la matriz y crear la huella correspondiente (cada huella de valida una sola vez). Se opto por este esquema ya que si bien acceder a la base de datos tiene un costo en performance, para matrices grandes el costo en de validación puede superar el del acceso a la base de datos.

- Dada la implementación del algoritmo de validación. Una secuencia de 5 letras iguales, es considerada como dos secuencias de 4 letras. Ya que dentro de una secuencia de 5 letras, caben dos secuencias de 4 letras.

- El algoritmo de validación soporta diferentes configuraciones de numero de secuencias requeridas y longitud de las mismas.

- El servicio de estadísticas de verificación considera las huellas registradas validas y no validas. No considera el numero de solicitudes validas/no validas realizadas.

- Para el servicio de estadísticas, de fondo se realiza un recuento de las huellas registradas. Este recuento resulto en las pruebas mas rápido que realizar una operación de agregación. Ya que el recuento utiliza un indice. Esta implementación se eligió considerando que el servicio de estadísticas posiblemente tenga un trafico mucho menor. Y la performance del mismo no es critica. Si así lo fuera y el recuento particular resultara muy costoso, una opción seria llevar una cuenta explicita en la base de datos.

___

### Api URL: https://fingercognition.herokuapp.com/

En este momento la Api se encuentra hosteada en Heroku.
**Aviso**: Pasados los 30 minutos sin uso, Heroku detiene el proceso. Este se reinicia al recibir un request. El reinicio puede demorar unos 10/15 segundos.

#### Validacion de Huellas

    URL: /fingerPrint
    METODO: POST
    Request Body:
        {
            "matrix" : ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
        }

#### Obtener Estadisticas de Verificacion

    URL: /stats
    METODO: GET
    Response Body:
    {
        "countcount_valid_fingerPrint" : 40,
        "count_not_valid_fingerPrint" : 100.
        "ratio": 0.4
     }

___

### Instrucciones Build | Deploy:

El despligue recomendado es mediante un container de Docker.

##### Requisitos:
- [Maven](https://maven.apache.org/install.html)
- [Docker](https://docs.docker.com/install/)
- [docker-compose](https://docs.docker.com/compose/install/)

#### Paso 1:
Descargar o clonar el proyecto.

#### Paso 2:
Abrir un terminal/consola en el directorio base del proyecto.

#### Paso 3:
Ejecutar este comando de Maven para compilar la imagen de Docker que contendra la aplicacion.

    mvn install dockerfile:build

Se creara la imagen **diegodc/fingercognition**

#### Paso 4: 
Ahora docker-compose se encargara de crear y ejecutar el contenedor de la aplicacion, y tambien el contenedor con la base de datos. Ademas realiza la conexion entre los contenedores.

##### Api URL: http://localhost:8080

- Crear los servicios y ejecutar

      docker-compose up -d

Se incluyo el servicio **mongo-express**, un cliente web para MongoDb, se puede acceder mediante http://localhost:8081

- Detener todos los servicios (sin eliminar los contenedores)

      docker-compose stop
    
- Iniciar los servicios

      docker-compose start
    
- Detener los servicios y eliminar los contenedores

      docker-compose down
