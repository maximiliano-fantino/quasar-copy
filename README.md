# Quasar

App deployada en heroku:

Url: https://safe-cliffs-59272.herokuapp.com/

Cómo instalar:

git clone https://github.com/matiescobar/Quasar.git

Este es un repositorio privado, pedir acceso.

Parandote sobre el home del proyecto, podes correr la aplicacion con el siguiente codigo por cmd:

java -jar target/spring-boot-rest-1.0.jar

(La aplicacion correra escuchando localhost:8080/)

Endpoints:

POST("/topsecret")
POST("/topsecret_split/{satellite_name}")
GET("/topsecret_split")

Podes usar por ejemplo Postman, para pegarle a cada endpoint.

Uso de la aplicacion:

La aplicación consta de 3 satelites, los cuales contamos con la informacion de sus coordenadas (tener en cuenta que se trata de coordenadas dadas en el plano cartesiano X e Y, y no en el espacio). Cada satelite recibira la distancia de su posicion actual hacia una nave de la cual se intentará conocer sus posicion y el mensaje que esta emitiendo.
Para esto, la aplicacion resuelve esta necesidad usando la interseccion entre 3 circunferencias, las cuales se juntan en un punto y esa será la posicion de la nave, usando como radio la distancia de cada satelite hacia la nave.

Ejemplo valido:

POST al endpoint "/topsecret" y usar el siguiente payload como body formato json:

{
"satelites": [
    {
    "name": "kenobi",
    "distance": 500,
    "message": ["", "es", "un", "mensaje", "secreto"]
},
{
    "name": "skywalker",
    "distance": 202,
    "message": ["", "es", "", "",  "secreto"]
},
{
    "name": "sato",
    "distance": 560,
    "message": ["este", "", "un",  "", ""]
}
]
}

Adicionalmente podras hacer un POST al endpoint "/topsecret_split/{satellite_name}" y pasar como variable en la url el nombre del satelite, y en el body un payload como el siguiente ejemplo:

POST: "/topsecret_split/kenobi"

 {
    "distance": 500,
    "message": ["", "es", "un", "mensaje", "secreto"]
}

Haciendo GET al endpoint "/topsecret_split" podras recibir la informacion de la nave en caso de que esto sea posible.
