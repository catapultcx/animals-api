package cx.catapult.animals.web;


import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Type;
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

    private Animal animal = new Animal("Tom", "Bob cat", "grey", "amphibian");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<Animal> response = template.postForEntity(base.toString(), animal, Animal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(animal.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(animal.getDescription());
        assertThat(response.getBody().getColour()).isEqualTo(animal.getColour());
        assertThat(response.getBody().getType()).isEqualTo(animal.getType());
    }

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() throws Exception {
        Animal created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void updateShouldWork() throws Exception {
        ResponseEntity<Animal> createResponse = template.postForEntity(base.toString(), animal, Animal.class);
        Animal updatedAnimal = createResponse.getBody();
        updatedAnimal.setName(animal.getName()+"-updated");
        updatedAnimal.setDescription(animal.getDescription()+"-updated");
        updatedAnimal.setColour(animal.getColour()+"-updated");
        updatedAnimal.setType(Type.get("reptiles"));

        HttpEntity<Animal> requestEntity = new HttpEntity<>(updatedAnimal);
        ResponseEntity<Animal> updateResponse = template.exchange(base.toString(), HttpMethod.PUT, requestEntity, Animal.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody().getId()).isNotEmpty();
        assertThat(updateResponse.getBody().getName()).isEqualTo(updatedAnimal.getName());
        assertThat(updateResponse.getBody().getDescription()).isEqualTo(updatedAnimal.getDescription());
        assertThat(updateResponse.getBody().getColour()).isEqualTo(updatedAnimal.getColour());
        assertThat(updateResponse.getBody().getType()).isEqualTo(updatedAnimal.getType());
    }

    @Test
    public void deleteShouldWork() throws Exception {
        Animal created = create("Test 1");
        ResponseEntity<Void> response = template.exchange(base.toString() + "/" + created.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    Animal create(String name) {
        Animal created = template.postForObject(base.toString(), new Animal(name, name, name, name), Animal.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
