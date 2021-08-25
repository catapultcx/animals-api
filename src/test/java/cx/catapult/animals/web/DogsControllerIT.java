package cx.catapult.animals.web;

import cx.catapult.animals.domain.Dog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class DogsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Dog dog = new Dog("Spike", "A dog");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/dogs");
    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<Dog> response = template.postForEntity(base.toString(), dog, Dog.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(dog.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(dog.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(dog.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void getShouldWork() throws Exception {
        Dog created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void deleteShouldWork() throws Exception {
        Dog created = create("Test 1");
        final ResponseEntity<Dog> response = template.exchange(base.toString() + "/" + created.getId(), HttpMethod.DELETE, null, Dog.class);
        final Dog deleted = response.getBody();
        assertThat(deleted.getName()).isEqualTo(created.getName());
        assertThat(deleted.getDescription()).isEqualTo(created.getDescription());
    }

    @Test
    public void deleteShouldHandleNonExisting() throws Exception {
        final ResponseEntity<String> response = template.exchange(base.toString() + "/nonexisting", HttpMethod.DELETE, null, String.class);
        assertThat(response.getBody()).isEqualTo(null);
    }

    private Dog create(String name) {
        Dog created = template.postForObject(base.toString(), new Dog(name, name), Dog.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
