package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    private Animal animal = new BaseAnimal("MM1", "a mammal one", "white", Type.MAMMALS);

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

    @Test
    public void createShouldWork() {
        ResponseEntity<BaseAnimal> response = template.postForEntity(base.toString(), animal, BaseAnimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(animal.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(animal.getDescription());
        assertThat(response.getBody().getType()).isEqualTo(animal.getType());
        assertThat(response.getBody().getColor()).isEqualTo(animal.getColor());
    }

    @Test
    public void testGetAnimal() {
        final String animalName = "carey";
        Animal created = create(animalName);
        ResponseEntity<BaseAnimal> response = template.getForEntity(base.toString() + "/" + created.getId(), BaseAnimal.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualToIgnoringGivenFields(created, "id");
    }

    private Animal create(String name) {
        Animal animal = new BaseAnimal(name, name, "brown", Type.INVERTEBRATE);
        Animal created = template.postForObject(base.toString(), animal, BaseAnimal.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }

}
