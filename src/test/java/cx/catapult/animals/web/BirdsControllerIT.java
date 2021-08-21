package cx.catapult.animals.web;


import cx.catapult.animals.domain.Bird;
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

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb"
        })
public class BirdsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Bird bird = new Bird("Tom", "Bobs bird");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/birds");
    }

    @Test
    public void createShouldWork() {
        ResponseEntity<Bird> response = template.postForEntity(base.toString(), bird, Bird.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(bird.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(bird.getDescription());
        assertThat(response.getBody().getAnimalGroup()).isEqualTo(bird.getAnimalGroup());
    }

    @Test
    public void allShouldWork() {
        Collection<?> items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(4);
    }

    @Test
    public void getShouldWork() {
        Bird created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    Bird create(String name) {
        Bird created = template.postForObject(base.toString(), new Bird(name, name), Bird.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
