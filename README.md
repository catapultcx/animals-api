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

### Exercise Updates

Solution for Requirements

1. Run services together with docker-compose
    * New docker-compose file (docker-compose-all.yml) has been added to bring up both the services at the same time
      Following environment variables have to be supplied
      * ANIMALS_API_IMG
      * ANIMALS_API_TAG
      * ANIMALS_APP_IMG
      * ANIMALS_APP_TAG
    * Image should have been created and pushed to some registry before
    * The command is `docker-compose -f docker-compose-all.yml up`
    
2. Profile service performance      
    * New Jmeter test plan (api-app-jmeter-test-plan.jmx) has been added
    * The test plan can test API & UI separately and both together. 
    * Number of users have been configured as 50
    * Duration assertion has been configured with 100ms
    * Import the jmx file into jmeter and run it against the API & APP services
