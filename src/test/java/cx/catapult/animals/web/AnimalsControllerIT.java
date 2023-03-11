package cx.catapult.animals.web;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
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

import static cx.catapult.animals.domain.Group.AMPHIBIAN;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AnimalsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private final BaseAnimal patchy = new BaseAnimal("Patchy", "A donkey", Group.MAMMALS, "Donkey");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

    @Test
    public void createShouldWork() {
        ResponseEntity<BaseAnimal> response = template.postForEntity(base.toString(), patchy, BaseAnimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(patchy.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(patchy.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(patchy.getGroup());
    }

    @Test
    public void allShouldWork() {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() {
        BaseAnimal created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    BaseAnimal create(String name) {
        final String type = "type";
        final String description = "description";
        final Group group = AMPHIBIAN;
        BaseAnimal created = template.postForObject(base.toString(), new BaseAnimal(name, name, description, group, type), BaseAnimal.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        assertThat(created.getType()).isEqualTo(type);
        assertThat(created.getDescription()).isEqualTo(description);
        assertThat(created.getGroup()).isEqualTo(group);
        return created;
    }
}
