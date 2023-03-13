package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.Collection;
import java.util.Objects;
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
import org.springframework.web.util.UriComponentsBuilder;
import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.AnimalType;
import cx.catapult.animals.domain.ErrorResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AnimalControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private final Animal animal = new Animal("Tom", "Bob cat", "grey", "amphibian");

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
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(animal.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(animal.getDescription());
        assertThat(response.getBody().getColour()).isEqualTo(animal.getColour());
        assertThat(response.getBody().getType()).isEqualTo(animal.getType());
    }

    @Test
    public void allShouldWork() {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() {
        Animal created = create();
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void getShouldFail() {
        ResponseEntity<ErrorResponse> response = template.getForEntity(base.toString() + "/123", ErrorResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("Animal not found for id: 123");
    }

    @Test
    public void updateShouldWork() {
        ResponseEntity<Animal> createResponse = template.postForEntity(base.toString(), animal, Animal.class);
        Animal updatedAnimal = createResponse.getBody();
        Objects.requireNonNull(updatedAnimal).setName(animal.getName() + "-updated");
        updatedAnimal.setDescription(animal.getDescription() + "-updated");
        updatedAnimal.setColour(animal.getColour() + "-updated");
        updatedAnimal.setType(AnimalType.get("reptiles"));

        HttpEntity<Animal> requestEntity = new HttpEntity<>(updatedAnimal);
        ResponseEntity<Animal> updateResponse = template.exchange(base.toString(), HttpMethod.PUT, requestEntity, Animal.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(updateResponse.getBody()).getId()).isNotEmpty();
        assertThat(updateResponse.getBody().getName()).isEqualTo(updatedAnimal.getName());
        assertThat(updateResponse.getBody().getDescription()).isEqualTo(updatedAnimal.getDescription());
        assertThat(updateResponse.getBody().getColour()).isEqualTo(updatedAnimal.getColour());
        assertThat(updateResponse.getBody().getType()).isEqualTo(updatedAnimal.getType());
    }

    @Test
    public void updateShouldFail() {
        ResponseEntity<Animal> createResponse = template.postForEntity(base.toString(), animal, Animal.class);
        Animal updatedAnimal = createResponse.getBody();
        Objects.requireNonNull(updatedAnimal).setId("non-existing-id");
        updatedAnimal.setName(animal.getName() + "-updated");
        updatedAnimal.setDescription(animal.getDescription() + "-updated");
        updatedAnimal.setColour(animal.getColour() + "-updated");
        updatedAnimal.setType(AnimalType.get("reptiles"));

        HttpEntity<Animal> requestEntity = new HttpEntity<>(updatedAnimal);
        ResponseEntity<ErrorResponse> updateResponse = template.exchange(base.toString(), HttpMethod.PUT, requestEntity, ErrorResponse.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(updateResponse.getBody()).getMessage()).isEqualTo("Animal not found for id: non-existing-id");
    }

    @Test
    public void deleteShouldWork() {
        Animal created = create();
        ResponseEntity<Void> response = template.exchange(base.toString() + "/" + created.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteShouldFail() {
        ResponseEntity<ErrorResponse> response = template.exchange(base.toString() + "/123", HttpMethod.DELETE, HttpEntity.EMPTY, ErrorResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("Animal not found for id: 123");
    }

    @Test
    public void filterShouldWorkWithEmptyResults() {
        String url = UriComponentsBuilder.fromHttpUrl(base.toString())
                .queryParam("name", "test")
                .encode().toUriString();

        Collection items = template.getForObject(url, Collection.class);
        assertThat(items.size()).isEqualTo(0);
    }

    @Test
    public void filterShouldWorkWithResults() {
        String url = UriComponentsBuilder.fromHttpUrl(base.toString())
                .queryParam("name", "Tigger")
                .encode().toUriString();

        Collection items = template.getForObject(url, Collection.class);
        assertThat(items.size()).isEqualTo(1);
    }

    Animal create() {
        Animal created = template.postForObject(base.toString(), new Animal("Test 1", "Test 1", "Test 1", "Test 1"), Animal.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo("Test 1");
        return created;
    }
}

