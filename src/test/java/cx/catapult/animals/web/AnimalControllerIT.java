package cx.catapult.animals.web;


import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
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

    private Cat animal = new Cat("Tom", "Bob cat", "Orange");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<BaseAnimal> response = template.postForEntity(base.toString(), animal, BaseAnimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(animal.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(animal.getDescription());
        assertThat(response.getBody().getClassification()).isEqualTo(animal.getClassification());
        assertThat(response.getBody().getColour()).isEqualTo(animal.getColour());
    }

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() throws Exception {
        Cat created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    Cat create(String name) {
        Cat created = template.postForObject(base.toString(), new Cat(name, name, "Orange"), Cat.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        assertThat(created.getColour()).isEqualTo("Orange");
        return created;
    }
}
