package cx.catapult.animals.web;


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

import static cx.catapult.animals.web.CatsMappping.CATS_API_V1;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CatsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private final Cat TOM_CAT = new Cat("Tom", "Bob cat");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + CATS_API_V1);
    }

    @Test
    public void shouldCreate() {
        ResponseEntity<Cat> response = template.postForEntity(getBaseUrl(), TOM_CAT, Cat.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(TOM_CAT.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(TOM_CAT.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(TOM_CAT.getGroup());
    }

    @Test
    public void shouldLoadAll() {
        Collection items = template.getForObject(getBaseUrl(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void shouldLoadById() {
        final Cat created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(getBaseUrl() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void shouldDeleteById() {
        ResponseEntity<Cat> responseCreate = template.postForEntity(getBaseUrl(), TOM_CAT, Cat.class);
        assertThat(responseCreate.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final Cat created = responseCreate.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(TOM_CAT.getName());
        assertThat(created.getDescription()).isEqualTo(TOM_CAT.getDescription());
        assertThat(created.getGroup()).isEqualTo(TOM_CAT.getGroup());

        template.delete(getBaseUrl() + "/" + created.getId());

        ResponseEntity<String> response = template.getForEntity(getBaseUrl() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNull();
    }

    private Cat create(String name) {
        final Cat created = template.postForObject(getBaseUrl(), new Cat(name, name + " description"), Cat.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        assertThat(created.getDescription()).isEqualTo(name + " description");
        return created;
    }

    private String getBaseUrl() {
        return base.toString();
    }
}
