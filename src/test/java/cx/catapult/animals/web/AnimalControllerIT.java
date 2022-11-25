package cx.catapult.animals.web;


import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Iguana;
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
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AnimalControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Cat cat = new Cat("Tom", "Bob cat", "Orange");
    private Iguana iguana = new Iguana("Tom", "Bob cat", "Orange");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

    @Test
    public void createShouldWorkForCat() throws Exception {
        ResponseEntity<BaseAnimal> response = template.postForEntity(base.toString(), cat, BaseAnimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(cat.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(cat.getDescription());
        assertThat(response.getBody().getClassification()).isEqualTo(cat.getClassification());
        assertThat(response.getBody().getColour()).isEqualTo(cat.getColour());
    }

    @Test
    void createShouldWorkForIguana() throws Exception {
        ResponseEntity<BaseAnimal> response = template.postForEntity(base.toString(), iguana, BaseAnimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(requireNonNull(response.getBody()).getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(iguana.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(iguana.getDescription());
        assertThat(response.getBody().getClassification()).isEqualTo(iguana.getClassification());
        assertThat(response.getBody().getColour()).isEqualTo(iguana.getColour());
    }

    @Test
    @SuppressWarnings("unchecked")
    //TODO: Fix unchecked warning becuase of generic type added/missing from collection
    void allShouldWork() throws Exception {
        Collection<BaseAnimal> items = template.getForObject(base.toString(), Collection.class);
        assertThat(items).hasSizeGreaterThanOrEqualTo(8);
    }

    @Test
    void getCatShouldWork() throws Exception {
        Cat created = createCat("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }
    @Test
    void getIguanaShouldWork() throws Exception {
        Iguana created = createIguana("Test 2");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    private Cat createCat(String name) {
        Cat created = template.postForObject(base.toString(), new Cat(name, name, "Orange"), Cat.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        assertThat(created.getColour()).isEqualTo("Orange");
        return created;
    }
    private Iguana createIguana(String name) {
        Iguana created = template.postForObject(base.toString(), new Iguana(name, name, "Green"), Iguana.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        assertThat(created.getColour()).isEqualTo("Green");
        return created;
    }


}
