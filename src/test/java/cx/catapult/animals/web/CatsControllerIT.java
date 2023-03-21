package cx.catapult.animals.web;


import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URL;
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

        delete(cat.getId());
    }

    @Test
    public void updateShouldWork() throws Exception {
        Cat created = create("Test 1");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpEntity<Cat> requestEntity = new HttpEntity<>(new Cat("Test name", "Test description"), headers);

        HttpEntity<Cat> response = template.exchange(base.toString() + "/" + created.getId(), HttpMethod.PUT, requestEntity, Cat.class);

        Cat updated = response.getBody();

        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isNotEmpty();
        assertThat(updated.getId()).isEqualTo(created.getId());
        assertThat(updated.getName()).isEqualTo("Test name");
        assertThat(updated.getDescription()).isEqualTo("Test description");

        delete(updated.getId());
    }

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void allWithNameAndDescriptionFilterShouldWork() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(base.toString())
                .queryParam("name", "Tom")
                .queryParam("description", "Friend of Jerry");

        Collection items = template.getForObject(builder.build().toUri(), Collection.class);
        assertThat(items.size()).isEqualTo(1);
    }

    @Test
    public void allWithNameOnlyInFilterShouldWork() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(base.toString())
                .queryParam("name", "Tom");

        Collection items = template.getForObject(builder.build().toUri(), Collection.class);

        // filtering on "/all" endpoint is only applied if valid name *and* description are specified
        // otherwise all items are returned
        assertThat(items.size()).isEqualTo(7);
    }

    @Test
    public void allWithDescriptionOnlyInFilterShouldWork() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(base.toString())
                .queryParam("description", "Friend of Jerry");

        Collection items = template.getForObject(builder.build().toUri(), Collection.class);

        // filtering on "/all" endpoint is only applied if valid name *and* description are specified
        // otherwise all items are returned
        assertThat(items.size()).isEqualTo(7);
    }

    @Test
    public void allWithUnkownFilterShouldWork() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(base.toString())
                .queryParam("name", "Name")
                .queryParam("description", "Friend");

        Collection items = template.getForObject(builder.build().toUri(), Collection.class);

        assertThat(items.size()).isEqualTo(0);
    }

    @Test
    public void getShouldWork() throws Exception {
        Cat created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();

        delete(created.getId());
    }

    @Test
    public void deleteShouldWork() throws Exception {
        Cat created = create("Test 1");

        Cat deleted = delete(created.getId());

        assertThat(deleted).isNotNull();
        assertThat(deleted.getId()).isNotEmpty();
        assertThat(deleted.getId()).isEqualTo(created.getId());
        assertThat(deleted.getName()).isEqualTo("Test 1");
        assertThat(deleted.getDescription()).isEqualTo("Test 1");
    }

    Cat delete(String id) {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Cat> requestEntity = new HttpEntity<>(headers);

        HttpEntity<Cat> response = template.exchange(base.toString() + "/" + id, HttpMethod.DELETE, requestEntity, Cat.class);

        return response.getBody();
    }

    Cat create(String name) {
        Cat created = template.postForObject(base.toString(), new Cat(name, name), Cat.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
