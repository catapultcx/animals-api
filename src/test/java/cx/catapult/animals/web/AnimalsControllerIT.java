package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;

import java.net.URL;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Crustacean;

/**
 * A Spring Boot integration test for the {@link AnimalsController}.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AnimalsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

	@Test
	public void getShouldWork() {
		Cat created = createCat("Cat 1");
		String url = base.toString() + "/" + created.getId();

		ResponseEntity<? extends BaseAnimal> response = template.getForEntity(url, Cat.class);

		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getName()).isEqualTo("Cat 1");
	}

    @Test
    public <T extends BaseAnimal> void allShouldWork() {
		String name = "Animal 1";
		createCat(name);
		createCrustacean(name);
		String url = base.toString() + "?name=" + name;
		ParameterizedTypeReference<Collection<? extends BaseAnimal>> responseType = new ParameterizedTypeReference<Collection<? extends BaseAnimal>>() {};

		Collection<? extends BaseAnimal> animals = template.exchange(url, GET, null, responseType).getBody();

        assertThat(animals.size()).isEqualTo(2);
    }

	Cat createCat(String name) {
		String url = "http://localhost:" + port + "/api/1/cats";
		Cat created = template.postForObject(url, new Cat(name, name), Cat.class);
		assertThat(created.getId()).isNotEmpty();
		assertThat(created.getName()).isEqualTo(name);
		return created;
	}

	Crustacean createCrustacean(String name) {
		String url = "http://localhost:" + port + "/api/1/crustaceans";
		Crustacean created = template.postForObject(url, new Crustacean(name, name), Crustacean.class);
		assertThat(created.getId()).isNotEmpty();
		assertThat(created.getName()).isEqualTo(name);
		return created;
	}
}
