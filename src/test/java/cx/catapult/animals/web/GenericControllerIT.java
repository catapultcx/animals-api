package cx.catapult.animals.web;


import cx.catapult.animals.domain.Generic;
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

public class GenericControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Generic generic = new Generic("Tom", "Bob generic");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/generic");
    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<Generic> response = template.postForEntity(base.toString(), generic, Generic.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(generic.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(generic.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(generic.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(5);
    }

    @Test
    public void getShouldWork() throws Exception {
        Generic created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void updateShouldWork() throws Exception {
        ResponseEntity<Generic> response = template.postForEntity(base.toString(), generic, Generic.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Generic createdGeneric = response.getBody();
        assertThat(createdGeneric).isNotNull();
        assertThat(createdGeneric.getId()).isNotEmpty();
        String newName = "Macavity";
        createdGeneric.setName(newName);
        template.put(base.toString(), createdGeneric);
        ResponseEntity<String> getResponse = template.getForEntity(base.toString() + "/" + createdGeneric.getId(), String.class);
        assertThat(getResponse.getBody()).isNotEmpty();
        assertThat(getResponse.getBody()).contains(newName);
    }

    @Test
    public void deleteShouldWork() throws Exception {
        ResponseEntity<Generic> response = template.postForEntity(base.toString(), generic, Generic.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Generic createdGeneric = response.getBody();
        assertThat(createdGeneric).isNotNull();
        assertThat(createdGeneric.getId()).isNotEmpty();
        template.delete(base.toString() + "/" + createdGeneric.getId(), String.class);
        ResponseEntity<String> getResponse = template.getForEntity(base.toString() + "/" + createdGeneric.getId(), String.class);
        assertThat(getResponse.getBody()).isNull();
    }

    Generic create(String name) {
        Generic created = template.postForObject(base.toString(), new Generic(name, name), Generic.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
