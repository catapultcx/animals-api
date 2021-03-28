package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.BaseAmphibian;
import cx.catapult.animals.domain.Cat;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AmphibianControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private BaseAmphibian amphibian = new BaseAmphibian("Lizzie", "Sand Gecko");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/amphibians");
    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<BaseAmphibian> response = template.postForEntity(base.toString(), amphibian, BaseAmphibian.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(amphibian.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(amphibian.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(amphibian.getGroup());
    }
}
