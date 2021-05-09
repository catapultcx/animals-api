package cx.catapult.animals.web;

import cx.catapult.animals.domain.Reptile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import java.net.URL;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReptilesControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    private Reptile reptile = new Reptile("Leo", "Leo lizard");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/reptiles");
    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<Reptile> response = template.postForEntity(base.toString(), reptile, Reptile.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(reptile.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(reptile.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(reptile.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() throws Exception {
        Reptile created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void deleteShouldWork() throws Exception {
        Reptile created = create("Test 2");
        template.delete(base.toString() + "/" + created.getId());
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void updateShouldWork() throws Exception {
        Reptile created = create("Test 3");
        template.put(base.toString()+"/"+ created.getId(), new Reptile("Change name",
                "Change description"));
        ResponseEntity<Reptile> response = template.getForEntity(base.toString() + "/" + created.getId(), Reptile.class);
        assertThat(response.getBody().getId()).isEqualTo(created.getId());
        assertThat(response.getBody().getName()).isEqualTo("Change name");
        assertThat(response.getBody().getDescription()).isEqualTo("Change description");
        assertThat(response.getBody().getGroup()).isEqualTo(reptile.getGroup());
    }





    Reptile create(String name) {
        Reptile created = template.postForObject(base.toString(), new Reptile(name, name), Reptile.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
