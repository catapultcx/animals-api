package cx.catapult.animals.web;


import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Horse;
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

public class HorseControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Horse horse = new Horse("Robin", "Winner of jersey race");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/horse");
    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<Horse> response = template.postForEntity(base.toString(), horse, Horse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(horse.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(horse.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(horse.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() throws Exception {
        Horse created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void shouldDeleteHorse() throws Exception {
        Horse created = create("Test 1");
        ResponseEntity<Horse> actualResponse = template.exchange(base.toString() + "/" + created.getId(), HttpMethod.DELETE, null, Horse.class);
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(actualResponse.getBody()).isNotNull();
    }

    Horse create(String name) {
        Horse created = template.postForObject(base.toString(), new Horse(name, name), Horse.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
