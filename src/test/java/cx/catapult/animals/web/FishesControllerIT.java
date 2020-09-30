package cx.catapult.animals.web;


import cx.catapult.animals.domain.Fish;
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
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class FishesControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Fish fish = new Fish("Tom", "Bob fish");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/fishes");
    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<Fish> response = template.postForEntity(base.toString(), fish, Fish.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(fish.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(fish.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(fish.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() throws Exception {
        Fish created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void updateShouldWork() throws Exception {
        ResponseEntity<Fish> responsePre = template.postForEntity(base.toString(), fish, Fish.class);
        Map < String, String > params = new HashMap < String, String > ();
        Fish fish = new Fish("Tom updated", "Bob fish");
        template.put(base.toString() + "/" + responsePre.getBody().getId(), fish, params);
        ResponseEntity<Fish> response = template.getForEntity(base.toString() + "/" + responsePre.getBody().getId(), Fish.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(responsePre.getBody().getId());
        assertThat(response.getBody().getName()).isEqualTo(fish.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(fish.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(fish.getGroup());
    }

    @Test
    public void deleteShouldWork() throws Exception {
        ResponseEntity<Fish> responsePre = template.postForEntity(base.toString(), fish, Fish.class);
        Map < String, String > params = new HashMap < String, String > ();
        template.delete(base.toString() + "/" + responsePre.getBody().getId(), params);
        ResponseEntity<Fish> response = template.getForEntity(base.toString() + "/" + responsePre.getBody().getId(), Fish.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    Fish create(String name) {
        Fish created = template.postForObject(base.toString(), new Fish(name, name), Fish.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
