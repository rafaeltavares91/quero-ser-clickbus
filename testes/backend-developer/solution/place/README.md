An API that manage places. This application was developed as part of ClickBus's selective process.

## Table of Contents
- [Introduction](#introduction)
- [Technologies](#technologies)
- [How to Run the project](#how-to-run-the-project)
  - [Getting Started](#getting-started)
  - [Running the Application](#running-the-application)
  - [Running the Tests](#running-the-tests)
- [The Checkout and How to use the API](#the-checkout-and-how-to-use-the-api)
  - [Available Routes](#available-routes)  
  
## Introduction
An API that manage places: it allows to create, edit and get a specific place. Also, it can list and filter all places by name.

Some premises of the challenge:
- All API responses are JSON.
- The code was made using TDD and therefore are several unit tests. An integration test was included also.
- I followed the SOLID principles.

## Technologies
What was used:
- **[Docker](https://docs.docker.com)** and **[DockerCompose](https://docs.docker.com/compose/)** to provide a testing environment.
- **[PostgreSQL](https://www.postgresql.org/)** to store data.
- **[SpringBoot](https://spring.io/projects/spring-boot)** to create stand-alone, production-grade Spring based Applications.
- **[Swagger](https://swagger.io/)** to document the endpoints.
- **[Spring's MockMvc](https://spring.io/guides/gs/testing-web/)**, **[Mockito](https://site.mockito.org/)** and **[JUnit4](https://junit.org/junit4/)** to write the tests.

## How to Run the project
### Getting Started
To get started, you should have **Docker** and **Maven** installed. To verify if you already have docker compose run:
```
$ docker-compose --version
```
And to verify if you already have maven, run:
```
$ mvn --version 
```
Clone the repository **[quero-ser-clickbus](https://github.com/rafaeltavares91/quero-ser-clickbus)**:
```
$ git clone https://github.com/rafaeltavares91/quero-ser-clickbus.git
```
In your JAVA IDE, import the application as a maven project. Just for reference, this project was developed using **[IntelliJ IDEA](https://www.jetbrains.com/idea/)**.

### Running the Application
Build the project image:
```
$ docker-compose build
```
Then build the project using maven:
```
$  mvn clean install
```
After that, run the application:
```
$ docker-compose up
```
And you're already good to go!

### Running the Tests
In order to run the tests, go to **/quero-ser-clickbus/testes/backend-developer/solution/place/src/test/java**

## How to use the API
Once the project is running, we can check swagger services documentation with the link bellow: **[Swagger endpoints documentation](http://localhost:8080/swagger-ui.html)**.

Here I have a **[Postman Collection](https://github.com/rafaeltavares91/quero-ser-clickbus/blob/master/testes/backend-developer/solution/place/src/main/resources/static/ClickBusChallenge.postman_collection.json)** that you can use to validate the endpoints.

## Available Routes 

| Routes                 | Description                      | HTTP Methods |
|------------------------|----------------------------------|--------------|
|/places                 | register a new place             | POST         |
|/places/{id}            | update an existing place         | PUT          |
|/places/{id}            | get a place by the id            | GET          |
|/places/list            | list all the places              | GET          |
|/places/list/{name}     | get a place by name              | GET          |

