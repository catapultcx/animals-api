package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.BaseAmphibian;
import cx.catapult.animals.domain.Cat;
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

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        BaseAmphibian created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void deleteShouldWork() throws Exception {
        final BaseAmphibian created = create("Test 1");
        template.delete(base.toString() + "/" + created.getId());
        final Collection<BaseAmphibian> items = (Collection<BaseAmphibian>) template.getForObject(base.toString(), Collection.class);
        assertThat(items).doesNotContain(created);
    }

    private BaseAmphibian create(String name) {
        BaseAmphibian created = template.postForObject(base.toString(), new BaseAmphibian(name, name), BaseAmphibian.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
