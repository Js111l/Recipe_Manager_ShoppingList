### Recipe_Manager project

The API provides basic functionality of managing shopping lists.

## Table of contents

* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
API provides functionality of managing shopping lists.
It provides endpoints to create, retrieve, update, and delete shopping lists.
Each shopping list contains title and list of elements.

#### Paths

https://shopping-list-api-yglz.onrender.com/swagger-ui-doc.html

## Technologies

* Java 17
* Spring Boot 3.1.0
* Spring Data JPA (Hibernate) 3.1.0
* Docker 24.0.2
* Postgres 15

##### Libraries:

* Lombok

##### Testing:

* WebTestClient
* TestContainers

## Setup

API is running on server so in order to access the api you can simply refer to the url:
https://shopping-list-api-yglz.onrender.com/recipes
and apply your specified http method and params.

In order to get information about total number of requests refer to the url:
https://shopping-list-api-yglz.onrender.comactuator/metrics/http.server.requests


