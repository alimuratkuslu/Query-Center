# Query Center Application

Query Center is a web application that provides an interface to query and analyze data from mongodb Database. It lets users create schedules that will send them the result of those queries consistently. The application is built using Spring Boot and React, and can be run using Docker.

## Technologies

- Java 17
- Spring Boot 3.0
- React 18.2
- MongoDB
- Maven
- Junit 5 
- Mockito
- Integration Tests
- Circle CI
- Docker
- Docker Compose
- Github Actions
- OpenAPI

## Installation

1. Clone this repository: git clone https://github.com/alimuratkuslu/Query-Center.git
2. Navigate to the project directory: `cd querycenter` 
3. Run `docker-compose build` to build the images necessary to run the application.
4. Run `docker-compose up`  to start the application.
5. Once the containers are up and running, navigate to http://localhost:3000 to access the application.

## Prerequisites

- Maven or Docker

## Docker Run

The application can be built and run by the Docker engine. The Dockerfile has multistage build, so you do not need to build and run separately.

Please follow the below directions in order to build and run the application with Docker Compose;

```
$ cd querycenter
$ docker-compose build
$ docker-compose up -d
```

## Maven Run

To build and run the application with Maven, please follow the directions below;

```
$ cd querycenter
$ mvn clean install
$ mvn spring-boot:run
```



