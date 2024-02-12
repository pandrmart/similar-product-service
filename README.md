# similar-product-service

DOCUMENTACIÓN DEL SERVICIO
--------------------------

Para la implementación de este microservicio se han usado las siguentes herramientas:

- Eclipse IDE + Spring Tools 4
- Docker Desktop

y las siguentes librerías:

- SpringBoot Dev Tools
- Spring web
- OpenFeign
- Resilience4j

He elegido Feign como cliente REST para obtener la información de los productos a través del servicio mock proporcionado.
Se ha implementado tolerancia a fallos a través de las librerías Resilience4j y se ha definido su configuración
en application.yml. En las llamadas lentas entra en juego el circuit breaker y también se ha habilitado un time limiter
(con un valor alto para que se lleguen a ejecutar todos los request). En caso de que se abra el circuito se muestra traza
en logs y se intentan obtener los últimos registros de una caché local, aunque este punto se puede resolver de muchas formas.
En caso de error 404 o 500 al acceder a algunos de los productos, se devuelven los similares que sí se han podido localizar.


EJECUCIÓN CON DOCKER
--------------------

Ejecutar desde la raíz del proyecto:

.\mvnw clean package
docker build -t similar-product-service:v1 .
docker run -p 5000:5000 --name similar-product-service --network YOUR_NETWORK similar-product-service:v1

* Definir una red para que haya visibilidad entre los servicios
