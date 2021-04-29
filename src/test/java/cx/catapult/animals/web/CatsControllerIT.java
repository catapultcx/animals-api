package cx.catapult.animals.web;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.net.URL;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import cx.catapult.animals.domain.Cat;

/**
 * A Spring Boot integration test for the {@link CatsController}.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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
    public void createShouldWork() {
        ResponseEntity<Cat> response = template.postForEntity(base.toString(), cat, Cat.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(cat.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(cat.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(cat.getGroup());
    }

    @Test
    public void allShouldWork() {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() {
        Cat created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

	@Test
	public void updateShouldWork() {
		Cat created = create("Initial Cat");
		String url = base.toString() + "/" + created.getId();
		Cat updated = new Cat("Unknown Cat", "Unknown Cat");
		HttpEntity<Cat> requestEntity = new HttpEntity<>(updated);

		ResponseEntity<Void> response = template.exchange(url, PUT, requestEntity, Void.class);

		assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
	}

	@Test
	public void updateShouldWorkGivenUnknownId() {
		Cat unknownCrab = new Cat("Unknown Cat", "Unknown Cat");
		String url = base.toString() + "/unknownId";

		ResponseEntity<Void> response = template.exchange(url, PUT, new HttpEntity<>(unknownCrab), Void.class);

		assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
	}

	Cat create(String name) {
		Cat created = template.postForObject(base.toString(), new Cat(name, name), Cat.class);
		assertThat(created.getId()).isNotEmpty();
		assertThat(created.getName()).isEqualTo(name);
		return created;
	}
}
