package cx.catapult.animals.web;


import cx.catapult.animals.domain.Animal;
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

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AnimalControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Animal animal = new Animal("Tom", "Bob cat", "Black", "Cat");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

    @Test
    public void createShouldWork() {
        ResponseEntity<Animal> response = template.postForEntity(base.toString(), animal, Animal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(animal.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(animal.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(animal.getGroup());
    }

    @Test
    public void allShouldWork() {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() {
        Animal created = create("Test 1", "Sample Test", "Black", "Cat");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void updateShouldWork() {
        Animal createdAnimal = create("Test 1", "Sample Test", "Black", "Cat");
        createdAnimal.setColour("Black");
        HttpEntity<Animal> updateAnimal = new HttpEntity<>(createdAnimal);
        ResponseEntity<Animal> response = template.exchange(base.toString(), HttpMethod.PUT, updateAnimal, Animal.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(createdAnimal.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(createdAnimal.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(createdAnimal.getGroup());
    }

    @Test
    void deleteShouldWork() {
        Animal createdAnimal = create("Test 1", "Sample Test", "Black", "Cat");
        ResponseEntity<Void> response = template.exchange(base.toString() + "/" + createdAnimal.getId(),
                HttpMethod.DELETE, null, Void.class);
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    Animal create(String name, String description, String colour, String type) {
        Animal created = template.postForObject(base.toString(), new Animal(name, description, colour, type), Animal.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
