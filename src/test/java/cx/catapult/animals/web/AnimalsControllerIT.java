package cx.catapult.animals.web;


import cx.catapult.animals.domain.BaseAnimal;
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

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AnimalsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Bob cat", Group.MAMMALS);

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<BaseAnimal> response = template.postForEntity(base.toString(), cat, BaseAnimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(cat.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(cat.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(cat.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() throws Exception {
        BaseAnimal created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    BaseAnimal create(String name) {
        BaseAnimal created = template.postForObject(
                base.toString(), new BaseAnimal(name, name, name, name, Group.MAMMALS), BaseAnimal.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
    @Test
    void updateShouldWork() {
        BaseAnimal created = template.postForObject(
                base.toString(), new BaseAnimal("id", "name", "name", "name", "name", Group.MAMMALS), BaseAnimal.class);

        HttpEntity<BaseAnimal> requestUpdate = new HttpEntity<>(created);
        ResponseEntity<Void> response = template.exchange(base.toString(), HttpMethod.PUT, requestUpdate, Void.class);
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    void deleteShouldWork() {
        ResponseEntity<Void> response = template.exchange(base.toString() + "/testId", HttpMethod.DELETE, null, Void.class);
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }


}
