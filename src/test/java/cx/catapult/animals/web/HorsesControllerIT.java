package cx.catapult.animals.web;


import cx.catapult.animals.domain.ApiError;
import cx.catapult.animals.domain.Horse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
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
@Execution(ExecutionMode.SAME_THREAD)
class HorsesControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Horse horse;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        horse = new Horse("Spirit", "The Stallion");
        this.base = new URL("http://localhost:" + port + "/api/1/horses");
    }

    @Test
    @Order(1)
    void create_shouldInsertWhenValidRequest() {
        ResponseEntity<Horse> response = template.postForEntity(base.toString(), horse, Horse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(horse.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(horse.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(horse.getGroup());
    }

    @Test
    void create_shouldReturnUnsupportedMediaRequestWhenRequestIsNull() {
        ResponseEntity response = template.postForEntity(base.toString(), null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    void create_shouldReturnBadRequestWhenNameInRequestIsNull() {
        horse.setName(null);

        ResponseEntity<ApiError> response = template.postForEntity(base.toString(), horse, ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Name cannot be null or empty");
    }

    @Test
    void create_shouldReturnBadRequestWhenNameInRequestIsBlank() {
        horse.setName("");

        ResponseEntity<ApiError> response = template.postForEntity(base.toString(), horse, ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Name cannot be null or empty");
    }

    @Test
    void create_shouldReturnBadRequestWhenDescriptionInRequestIsNull() {
        horse.setDescription(null);

        ResponseEntity<ApiError> response = template.postForEntity(base.toString(), horse, ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Description cannot be null or empty");
    }

    @Test
    void create_shouldReturnBadRequestWhenDescriptionInRequestIsBlank() {
        horse.setDescription("");

        ResponseEntity<ApiError> response = template.postForEntity(base.toString(), horse, ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Description cannot be null or empty");
    }

    @Test
    @Order(2)
    void get_shouldGetValidRecord() {
        ResponseEntity<Horse> response = template.postForEntity(base.toString(), horse, Horse.class);

        response = template.getForEntity(base.toString() + "/" + response.getBody().getId(), Horse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(horse.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(horse.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(horse.getGroup());
    }

    @Test
    void get_shouldReturnBadRequestWhenIdIsNull() {
        ResponseEntity<ApiError> response = template.getForEntity(base.toString() + "/ ", ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void allShouldWork() {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(4);
    }
}
