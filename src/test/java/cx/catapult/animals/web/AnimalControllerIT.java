package cx.catapult.animals.web;


import cx.catapult.animals.domain.Animal;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AnimalControllerIT {
    private static final String BASE_ENDPOINT = "/owners/{ownerId}/animals";
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void createShouldWorkForAnimal() {
        RestAssured
            .with()
            .contentType(ContentType.JSON)
            .body(new Animal(null, "Cat", "Billy", "Brown", "Billy Brown"))
            .when()
            .pathParam("ownerId", "1234")
            .post(BASE_ENDPOINT)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .assertThat()
            .body("type", equalTo("Cat"));
    }

    @Test
    void allShouldWork() {
        var response = RestAssured
            .given()
            .pathParam("ownerId", "TEST")
            .get(BASE_ENDPOINT)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .response()
            .as(Collection.class);

            assertTrue(response.size() >= 1);
    }

    @Test
    void getAnimalShouldWork() {
        var saved = RestAssured
            .with()
            .contentType(ContentType.JSON)
            .body(new Animal(null, "Cat", "Billy", "Brown", "Billy Brown"))
            .when()
            .pathParam("ownerId", "1234")
            .post(BASE_ENDPOINT)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .response()
            .as(Animal.class);


        var response = RestAssured
            .given()
            .pathParam("ownerId", "1234")
            .pathParam("animalId", saved.id())
            .get(BASE_ENDPOINT + "/{animalId}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .response()
            .as(Animal.class);

        assertEquals("Billy", response.name());
    }

    @Test
    void deleteAnimalShouldWork() {
        var saved = RestAssured
            .with()
            .contentType(ContentType.JSON)
            .body(new Animal(null, "Cat", "Billy", "Brown", "Billy Brown"))
            .when()
            .pathParam("ownerId", "1234D")
            .post(BASE_ENDPOINT)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .response()
            .as(Animal.class);

        var getAllBeforeDelete = RestAssured
            .given()
            .pathParam("ownerId", "1234D")
            .get(BASE_ENDPOINT)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .response()
            .as(Collection.class);

        assertEquals(1, getAllBeforeDelete.size());

        RestAssured
            .given()
            .pathParam("ownerId", "1234D")
            .pathParam("animalId", saved.id())
            .delete(BASE_ENDPOINT + "/{animalId}")
            .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);

        var getAllAfterDelete = RestAssured
            .given()
            .pathParam("ownerId", "1234D")
            .get(BASE_ENDPOINT)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .response()
            .as(Collection.class);


        assertEquals(0, getAllAfterDelete.size());
    }

 @Test
    void updateAnimalShouldWork() {
        var saved = RestAssured
            .with()
            .contentType(ContentType.JSON)
            .body(new Animal(null, "Cat", "Billy", "Brown", "Billy Brown"))
            .when()
            .pathParam("ownerId", "1234U")
            .post(BASE_ENDPOINT)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .response()
            .as(Animal.class);

        var getAllBeforeUpdate = RestAssured
            .given()
            .pathParam("ownerId", "1234U")
            .get(BASE_ENDPOINT)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .response()
            .as(Collection.class);

        assertEquals(1, getAllBeforeUpdate.size());

       var update = RestAssured
           .with()
           .contentType(ContentType.JSON)
           .body(new Animal(saved.id(), "Cat", "Billy", "Blue", "An updated Billy not so Brown"))
           .when()
           .pathParam("ownerId", "1234U")
           .pathParam("animalId", saved.id())
           .put(BASE_ENDPOINT + "/{animalId}")
           .then()
           .statusCode(HttpStatus.SC_OK)
           .extract()
           .response()
           .as(Animal.class);

        var expected = new Animal(saved.id(), "Cat", "Billy", "Blue", "An updated Billy not so Brown");

        assertEquals(expected, update);
    }


}
