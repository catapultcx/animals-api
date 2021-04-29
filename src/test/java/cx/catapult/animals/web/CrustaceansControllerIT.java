package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
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

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
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
		ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
		assertThat(response.getBody()).isNotEmpty();
	}

	Crustacean create(String name) {
		Crustacean created = template.postForObject(base.toString(), new Crustacean(name, name), Crustacean.class);
		assertThat(created.getId()).isNotEmpty();
		assertThat(created.getName()).isEqualTo(name);
		return created;
	}
}