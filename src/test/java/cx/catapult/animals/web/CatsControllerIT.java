package cx.catapult.animals.web;


import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URL;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class CatsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private final Cat cat = new Cat("Tom", "Bob cat");

    @Autowired
    AnimalRepository repository;
    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/cats");
        this.repository.deleteAll();

    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<Cat> response = template.postForEntity(base.toString(), cat, Cat.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(cat.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(cat.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(cat.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        create("Test");
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        Cat created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    Cat create(String name) {
        return create(name,name);
    }
    Cat create(String name, String desc) {
        Cat created = template.postForObject(base.toString(), new Cat(name, desc), Cat.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
