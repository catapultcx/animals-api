package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Type;
import cx.catapult.animals.domain.UpdateAnimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.Collection;

import static org.apache.commons.lang3.RandomUtils.nextInt;
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
        template.exchange(base.toString(), HttpMethod.DELETE, null, Void.class);
    }

    @Test
    public void testCreate() {
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
        return createAnimal(animal);
    }

    private Animal createAnimal(Animal animal) {
        return template.postForObject(base.toString(), animal, BaseAnimal.class);
    }

    @Test
    public void testAll() {
        final String animalName = "Trace";
        create(animalName + nextInt(), "brown", Type.MAMMALS);
        create(animalName + nextInt(), "white", Type.BIRD);
        ResponseEntity<Collection<BaseAnimal>> animals = template.exchange(base.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<Collection<BaseAnimal>>() {});
        assertThat(animals.getBody().size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testDeleteAnimal() {
        final String animalName = "carey";
        Animal created = create(animalName);
        template.exchange(base.toString() + "/" + created.getId(), HttpMethod.DELETE, null, Void.class);
        ResponseEntity<BaseAnimal> response = template.getForEntity(base.toString() + "/" + created.getId(), BaseAnimal.class);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testUpdate() {
        final String animalName = "carey";
        Animal created = create(animalName);
        UpdateAnimal update = new UpdateAnimal(created.getName(), Type.FISH.name(), "red", created.getDescription());
        template.exchange(base.toString() + "/" + created.getId(), HttpMethod.PUT, new HttpEntity<>(update, null), Void.class);
        ResponseEntity<BaseAnimal> response = template.getForEntity(base.toString() + "/" + created.getId(), BaseAnimal.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("red");
        assertThat(response.getBody().getType().name()).isEqualTo("FISH");
    }

    private Animal create(String name, String color, Type type) {
        Animal animal = new BaseAnimal(name, name, color, type);
        return createAnimal(animal);
    }

}
