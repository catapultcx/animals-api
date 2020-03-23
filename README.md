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
    
Tests with coverage:

    mvn clean test
    
Build the `.jar`:

    mvn clean package    


Run with Docker (build jar first)

    docker-compose up


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