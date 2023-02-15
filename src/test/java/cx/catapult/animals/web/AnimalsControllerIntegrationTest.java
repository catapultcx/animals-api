package cx.catapult.animals.web;


import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.Collection;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalsControllerIntegrationTest {
    @LocalServerPort
    private int port;

    private URL base;
    private URL filterUrl;

    private final Animal animal = new Animal("Tom", "Bob cat", "blue");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/2/cats");
        this.filterUrl = new URL("http://localhost:" + port + "/api/2/filter");
    }

    @Test
    public void createShouldWork() {
        ResponseEntity<Animal> response = template.postForEntity(base.toString(), animal, Animal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(animal.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(animal.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(Group.MAMMALS);
    }

    @Test
    public void allShouldWork() {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() {
        Animal created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void animalController_whenDeleteIsCalled_shouldWork() {
        Animal created = create("Test To Delete");
        ResponseEntity<Void> response = template.exchange(base.toString() + "/" + created.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode()).isBetween(HttpStatus.OK, HttpStatus.NO_CONTENT);
    }

    @Test
    public void animalController_whenDeleteIsCalledForANotExistingItem_shouldFail() {
        ResponseEntity<Void> response = template.exchange(base.toString() + "/error", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode()).isGreaterThanOrEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void animalController_whenUpdateIsCalled_shouldWork() {
        Animal created = create("Test To Delete");
        created.setName("Test to Update");
        ResponseEntity<Animal> response = template.exchange(base.toString() + "/" + created.getId(), HttpMethod.PUT, new HttpEntity<>(created), Animal.class);
        assertThat(response.getStatusCode()).isBetween(HttpStatus.OK, HttpStatus.NO_CONTENT);
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("Test to Update");
    }

    @Test
    public void animalController_whenUpdateIsCalledWithAnObjectWithDifferentId_shouldFail() {
        Animal created = create("Test To Delete");
        created.setName("Test to Update");
        String realId = created.getId();
        created.setId("wrong_id_value");
        ResponseEntity<Animal> response = template.exchange(base.toString() + "/" + realId, HttpMethod.PUT, new HttpEntity<>(created), Animal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void animalController_whenUpdateIsCalledWithAnInvalidAnimal_shouldFail() {
        Animal created = create("Test To Delete");
        created.setName(null);
        ResponseEntity<Animal> response = template.exchange(base.toString() + "/" + created.getId(), HttpMethod.PUT, new HttpEntity<>(created), Animal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void animalController_whenUpdateIsCalledWithAnInvalidObject_shouldFail() {
        Animal created = create("Test To Delete");
        created.setId("unknown");
        ResponseEntity<String> response = template.exchange(base.toString() + "/" + created.getId(), HttpMethod.PUT, new HttpEntity<>(created), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void animalController_whenFilterIsCalled_shouldFilter() {
        Collection result = template.getForObject(filterUrl + "?names=Tom,Jerry", Collection.class);
        assertThat(result.size()).isEqualTo(2);

        Collection dogs = template.getForObject(filterUrl + "?names=Tom,Jerry&types=dog", Collection.class);
        assertThat(dogs.size()).isEqualTo(0);
    }

    Animal create(String name) {
        Animal created = template.postForObject(base.toString(), new Animal(name, name, "gray"), Animal.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }

}
