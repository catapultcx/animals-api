package cx.catapult.animals.web;


import cx.catapult.animals.domain.ApiError;
import cx.catapult.animals.domain.Cat;
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

    @Test
    void update_shouldUpdateIfRecordExists() {
        ResponseEntity<Cat> response = template.postForEntity(base.toString(), cat, Cat.class);
        Cat cat = response.getBody();
        cat.setName("Warrior");

        ResponseEntity<Void> updateResponse = template.exchange(base.toString(), HttpMethod.PUT, new HttpEntity<>(cat), Void.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

        response = template.getForEntity(base.toString() + "/" + response.getBody().getId(), Cat.class);

        assertThat(response.getBody().getName()).isEqualTo(cat.getName());
    }

    @Test
    void update_shouldUpdateIfRecordDoesntExists() {
        ResponseEntity<ApiError> response = template.exchange(base.toString(), HttpMethod.PUT, new HttpEntity<>(cat), ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Record not found");
    }

    @Test
    void update_shouldReturnUnsupportedMediaRequestWhenRequestIsNull() {
        ResponseEntity response = template.exchange(base.toString(), HttpMethod.PUT, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    void update_shouldReturnBadRequestWhenNameInRequestIsNull() {
        cat.setName(null);

        ResponseEntity<ApiError> response = template.exchange(base.toString(), HttpMethod.PUT, new HttpEntity<>(cat), ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Name cannot be null or empty");
    }

    @Test
    void update_shouldReturnBadRequestWhenNameInRequestIsBlank() {
        cat.setName("");

        ResponseEntity<ApiError> response = template.exchange(base.toString(), HttpMethod.PUT, new HttpEntity<>(cat), ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Name cannot be null or empty");
    }

    @Test
    void update_shouldReturnBadRequestWhenDescriptionInRequestIsNull() {
        cat.setDescription(null);

        ResponseEntity<ApiError> response = template.exchange(base.toString(), HttpMethod.PUT, new HttpEntity<>(cat), ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Description cannot be null or empty");
    }

    @Test
    void update_shouldReturnBadRequestWhenDescriptionInRequestIsBlank() {
        cat.setDescription("");

        ResponseEntity<ApiError> response = template.exchange(base.toString(), HttpMethod.PUT, new HttpEntity<>(cat), ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Description cannot be null or empty");
    }

    Cat create(String name) {
        Cat created = template.postForObject(base.toString(), new Cat(name, name), Cat.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
