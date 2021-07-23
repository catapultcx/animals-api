# Animals API

Animals API is a Spring Boot web application with a Swagger documented API.

# Getting Started

### Prerequisites

First install:

* Java 13
* Maven 3.6.3+

### Commands

Run the service:

    mvn spring-boot:run

Run MSSQL Docker container:

    docker-compose -f docker-compose-mysql.yml up

Tests with coverage (run docker container for MySQL first):

    mvn clean test
After testing bring down the container

Build the `.jar`:

    mvn clean package -Dmaven.test.skip=true  


Run with Docker (build jar first)

    docker-compose up

This brings up both application and MySQL server in docker containers

### URLs

Once started the API will be available at:

 * http://localhost:8080
 
 
API docs can be found at:

* http://localhost:8080/api-docs
* http://localhost:8080/swagger-ui.html
 

### Reference Documentation
For further reference, please consider the following sections:

* [Swagger](https://www.baeldung.com/spring-rest-openapi-documentation)
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/maven-plugin/)
* [JaCoCo and maven](https://www.baeldung.com/jacoco)
* [JaCoCo and maven](https://automationrhapsody.com/automated-code-coverage-of-unit-tests-with-jacoco-and-maven)
* [Spring Boot guide](https://spring.io/guides/gs/spring-boot)
* [Spring Boot and Docker](https://spring.io/guides/gs/spring-boot-docker/)
* [MySql](https://spring.io/guides/gs/accessing-data-mysql/)