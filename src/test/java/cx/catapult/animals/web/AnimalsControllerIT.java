package cx.catapult.animals.web;


import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.AnimalFactory;
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

public class AnimalsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private final Animal aCat = AnimalFactory.aCat();

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

    @Test
    public void createShouldWork() throws Exception {
        var response = template.postForEntity(base.toString(), aCat, Animal.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var animalResponse = response.getBody();
        assertThat(animalResponse.getId()).isNotEmpty();
        assertThat(animalResponse.getName()).isEqualTo(aCat.getName());
        assertThat(animalResponse.getDescription()).isEqualTo(aCat.getDescription());
        assertThat(animalResponse.getType()).isEqualTo(aCat.getType());
        assertThat(animalResponse.getColour()).isEqualTo(aCat.getColour());
    }

    @Test
    public void allShouldWork() throws Exception {
        var items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(6);
    }

    @Test
    public void getShouldWork() throws Exception {
        var created = template.postForObject(base.toString(), AnimalFactory.aCat(), Animal.class);

        var response = template.getForEntity(base.toString() + "/" + created.getId(), Animal.class);

        var animalResponse = response.getBody();
        assertThat(animalResponse.getType()).isEqualTo(created.getType());
        assertThat(animalResponse.getDescription()).isEqualTo(created.getDescription());
    }
}
