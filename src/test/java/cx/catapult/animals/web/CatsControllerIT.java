package cx.catapult.animals.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CatsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Cat cat = new Cat("Tom", "Bob cat");

    @Autowired
    private TestRestTemplate template;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/cats");
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
        Cat created = template.postForObject(base.toString(), new Cat(name, name), Cat.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }



    @Test
    public void updateShouldWork() throws Exception {
        ResponseEntity<Cat> response = template.postForEntity(base.toString(), cat, Cat.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Cat created = response.getBody();
        created.setName("updatedName");
        created.setDescription("updatedDesc");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Cat> requestEntity = new HttpEntity<>(created, headers);
        ResponseEntity<Boolean> responseEntity = template.exchange(base.toString() + "/update", HttpMethod.PUT, requestEntity, Boolean.class, created.getId());
        boolean updated = responseEntity.getBody();
        assertThat(updated).isTrue();

        response = template.getForEntity(base.toString() + "/" + created.getId(), Cat.class);
        assertThat(response.getBody().getName()).isEqualTo(created.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(created.getDescription());
    }

    @Test
    public void updateShouldNotWork() throws Exception {
        ResponseEntity<Cat> response = template.postForEntity(base.toString(), cat, Cat.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Cat created = response.getBody();
        created.setId("invalid id");
        created.setName("updatedName");
        created.setDescription("updatedDesc");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Cat> requestEntity = new HttpEntity<>(created, headers);
        ResponseEntity<Boolean> responseEntity = template.exchange(base.toString() + "/update", HttpMethod.PUT, requestEntity, Boolean.class, created.getId());
        boolean updated = responseEntity.getBody();
        assertThat(updated).isFalse();
    }

    @Test
    public void deleteShouldWork() throws Exception {
        ResponseEntity<Cat> response = template.postForEntity(base.toString(), cat, Cat.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        Cat created = response.getBody();

        ResponseEntity<Void> deleted = template.exchange(base.toString() + "/" + created.getId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleted.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleted.getBody()).isNull();

        response = template.getForEntity(base.toString() + "/" + created.getId(), Cat.class);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void searchShouldWork() throws Exception {
        String name = "";
        String desc = "";
        ResponseEntity<Collection> response = template.getForEntity(base.toString() + "/search?name="+name +"&desc=" +desc, Collection.class);
        assertThat(response.getBody().size()).isGreaterThanOrEqualTo(7);

        name = "tiger";
        response = template.getForEntity(base.toString() + "/search?name="+name +"&desc=" +desc, Collection.class);
        assertThat(response.getBody().size()).isGreaterThanOrEqualTo(1);
    }
}
