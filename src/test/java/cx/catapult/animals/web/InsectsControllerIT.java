package cx.catapult.animals.web;


import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Insect;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class InsectsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Insect insect = new Insect("Jimminy", "Cricket");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/insects");
    }

    @Test
    public void createShouldWork() {
        ResponseEntity<Insect> response = template.postForEntity(base.toString(), insect, Insect.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(insect.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(insect.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(insect.getGroup());
    }

    @Test
    public void allShouldWork() {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        Cat created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    Cat create(String name) {
        Cat created = template.postForObject(base.toString(), new Cat(name, name), Cat.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
