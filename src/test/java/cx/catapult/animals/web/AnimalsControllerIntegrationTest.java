package cx.catapult.animals.web;


import cx.catapult.animals.AnimalsApiApplication;
import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Group;
import cx.catapult.animals.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URL;
import java.util.Collection;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {AnimalsApiApplication.class})
public class AnimalsControllerIntegrationTest {
    @LocalServerPort
    private int port;

    private URL catsUrl;
    private URL base;

    private final Animal animal = new Animal("Tom", "Bob cat", "blue");

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private AnimalRepository animalRepository;

    @BeforeEach
    public void setUp() throws Exception {
        this.catsUrl = new URL("http://localhost:" + port + "/api/2/cats");
        this.base = new URL("http://localhost:" + port + "/api/2");
    }

    @Test
    public void createShouldWork() {
        ResponseEntity<Animal> response = template.postForEntity(catsUrl.toString(), animal, Animal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(animal.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(animal.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(Group.MAMMALS);
    }

    @Test
    public void allShouldWork() {
        Collection items = template.getForObject(catsUrl.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        Animal created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(catsUrl.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void animalController_whenDeleteIsCalled_shouldWork() {
        Animal created = create("Test To Delete");
        ResponseEntity<String> response = template.exchange(catsUrl.toString() + "/" + created.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.NO_CONTENT);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.valueOf("text/plain;charset=UTF-8"));
    }

    @Test
    public void animalController_whenDeleteIsCalledForANotExistingItem_shouldFail() {
        ResponseEntity<Void> response = template.exchange(catsUrl.toString() + "/error", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    public void animalController_whenUpdateIsCalled_shouldWork() {
        Animal created = create("Test To Delete");
        created.setName("Test to Update");
        ResponseEntity<Animal> response = template.exchange(catsUrl.toString() + "/" + created.getId(), HttpMethod.PUT, new HttpEntity<>(created), Animal.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.NO_CONTENT);
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("Test to Update");
    }

    @Test
    public void animalController_whenUpdateIsCalledWithAnObjectWithDifferentId_shouldFail() {
        Animal created = create("Test To Delete");
        created.setName("Test to Update");
        String realId = created.getId();
        created.setId("wrong_id_value");
        ResponseEntity<Animal> response = template.exchange(catsUrl.toString() + "/" + realId, HttpMethod.PUT, new HttpEntity<>(created), Animal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void animalController_whenUpdateIsCalledWithAnInvalidAnimal_shouldFail() {
        Animal created = create("Test To Delete");
        created.setName(null);
        ResponseEntity<Animal> response = template.exchange(catsUrl.toString() + "/" + created.getId(), HttpMethod.PUT, new HttpEntity<>(created), Animal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void animalController_whenUpdateIsCalledWithAnInvalidObject_shouldFail() {
        Animal created = create("Test To Delete");
        created.setId("unknown");
        ResponseEntity<String> response = template.exchange(catsUrl.toString() + "/" + created.getId(), HttpMethod.PUT, new HttpEntity<>(created), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void animalController_whenFilterIsCalled_shouldFilter() {
        create("Tom", "Friend of Jerry", "blue");
        create("Jerry", "Not really a cat", "blue");
        create("Bili", "Furry cat", "black");
        create("Smelly", "Cat with friends", "black");
        create("Tiger", "Large cat", "yellow");
        Collection result = template.getForObject(base + "/filter?names=Tom,Jerry", Collection.class);
        assertThat(result.size()).isEqualTo(2);

        Collection dogs = template.getForObject(base + "/filter?names=Tom,Jerry&types=dog", Collection.class);
        assertThat(dogs.size()).isEqualTo(0);
    }

    @Test
    public void animalController_whenTypesCalled_shouldReturnTypes() {
        Collection result = template.getForObject(base + "/types", Collection.class);
        assertThat(result.size()).isEqualTo(9);
    }

    @Test
    public void animalController_whenGroupsCalled_shouldReturnGroups() {
        Collection result = template.getForObject(base + "/groups", Collection.class);
        assertThat(result.size()).isEqualTo(Group.values().length);
    }

    @Test
    public void animalController_whenRegisterCalled_shouldRegister() {
        template.exchange(base + "/register/mammals/monkey",HttpMethod.GET, HttpEntity.EMPTY, String.class);
        Collection result = template.getForObject(base + "/types", Collection.class);
        assertThat(result.size()).isEqualTo(10);

        Animal created = template.postForObject(base + "/monkeys", new Animal("Bobo", "My monkey", "gray"), Animal.class);
        assertThat(created).isNotNull();
    }

    @Test
    public void animalController_whenRegisterCalledWithWrongType_shouldFail() {
        ResponseEntity<String> response = template.exchange(base + "/register/mamma/monkey",HttpMethod.GET, HttpEntity.EMPTY, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    Animal create(String name) {
        return create(name,name, "gray");
    }
    Animal create(String name, String desc, String color) {
        Animal created = template.postForObject(catsUrl.toString(), new Animal(name, desc, color), Animal.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }

}
