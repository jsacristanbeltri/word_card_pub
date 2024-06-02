# Word Card

Este proyecto es una API RESTful para gestionar barajas y tarjetas de palabras de palabras. Proporciona endpoints para crear, leer, actualizar y eliminar tarjetas y barajas, así como también maneja mensajes entrantes y salientes con RabbitMQ para procesamiento asíncrono y manejo de errores.

## Tabla de Contenidos

1. [Descripción](#descripción)
2. [Requisitos](#requisitos)
3. [Instalación](#instalación)
4. [Configuración](#configuración)
5. [Documentación](#documentación)
6. [Uso](#uso)
7. [Endpoints](#endpoints)

## Descripción
Esta API RESTful forma parte del backend de una aplicacion en la cual un usuario puede aprender vocabulario con el metodo de flashCards en cualquier idioma.

Utiliza una arquitectura cliente-servidor sin estado, donde los recursos están representados por URIs (Uniform Resource Identifiers) y se accede a ellos a través de métodos HTTP estándar, como GET, POST, PUT y DELETE. Además, la API utiliza JSON como formato de intercambio de datos, lo cual es común en las APIs RESTful.
Tambien utiliza una base de datos Postgresql , un gestor de mensajes (RabbirMq) y seguridad con Spring Security y Keycloak. 


## Requisitos

- Docker
  - Servidor keycloak
  - Base de datos Postgresql
  - Message broker Rabbitmq
- Postman
- Gestor de bases de datos Dbeaver

## Instalación

Pasos detallados para instalar la API:

1. Clonar el repositorio:
2. Instalar docker desktop
3. Setear en fichero application.properties el entorno pre

   spring.profiles.active=pre

4. Levantar contenedores necesarios, para ello ejecutar el comando docker docker-compose up -d --build

## Configuración

Instrucciones para configurar la API, incluyendo variables de entorno, archivos de configuración, etc.

Ejemplo:
1. Crear un archivo `.env` en el directorio raíz del proyecto y añadir las siguientes variables:
   ```env
   DB_HOST=localhost
   DB_PORT=5432
   DB_USER=usuario
   DB_PASS=contraseña

## Documentación

Javadoc: [index.html](javadocs%2Findex.html)

Swagger: http://localhost:8080/swagger-ui/index.html#/

## Uso

1. Levantar API con IntelliJ
2. Solicitar token a keycloak 
3. Realizar llamadas a endpoints con autenticacion bearer token

## Endpoints

Swagger: http://localhost:8080/swagger-ui/index.html#/