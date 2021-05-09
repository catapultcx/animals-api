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
}
