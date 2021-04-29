package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.net.URL;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import cx.catapult.animals.domain.Crustacean;

/**
 * A Spring Boot integration test for the {@link CrustaceansController}.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrustaceansControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Crustacean crustacean = new Crustacean("Crabby", "Crabby the crab");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/crustaceans");
    }

    @Test
    public void createShouldWork() {
        ResponseEntity<Crustacean> response = template.postForEntity(base.toString(), crustacean, Crustacean.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(crustacean.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(crustacean.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(crustacean.getGroup());
    }

	@Test
	public void allShouldWork() {
		Collection items = template.getForObject(base.toString(), Collection.class);

		assertThat(items.size()).isGreaterThanOrEqualTo(3);
	}

	@Test
	public void getShouldWork() {
		Crustacean created = create("Test 1");

		ResponseEntity<Crustacean> response = template.getForEntity(base.toString() + "/" + created.getId(), Crustacean.class);

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getName()).isEqualTo("Test 1");
	}

	@Test
	public void updateShouldWork() {
		Crustacean created = create("Test 1");
		String url = base.toString() + "/" + created.getId();
		Crustacean updated = new Crustacean("Updated", "Updated");
		HttpEntity<Crustacean> requestEntity = new HttpEntity<>(updated);

		ResponseEntity<Void> response = template.exchange(url, PUT, requestEntity, Void.class);

		assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
	}

	@Test
	public void updateShouldWorkGivenUnknownId() {
		Crustacean unknownCrab = new Crustacean("Unknown Crab", "Unknown Crab");
		String url = base.toString() + "/unknownId";

		ResponseEntity<Void> response = template.exchange(url, PUT, new HttpEntity<>(unknownCrab), Void.class);

		assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
	}

	@Test
	public void deleteShouldWork() {
		Crustacean created = create("Test 1");
		String url = base.toString() + "/" + created.getId();

		ResponseEntity<Void> response = template.exchange(url, DELETE, null, Void.class);

		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

	@Test
	public void deleteShouldWorkGivenUnknownId() {
		String url = base.toString() + "/unknown-id";

		ResponseEntity<Void> response = template.exchange(url, DELETE, null, Void.class);

		assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }

	Crustacean create(String name) {
		Crustacean created = template.postForObject(base.toString(), new Crustacean(name, name), Crustacean.class);
		assertThat(created.getId()).isNotEmpty();
		assertThat(created.getName()).isEqualTo(name);
		return created;
	}
}
