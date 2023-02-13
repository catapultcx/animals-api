package cx.catapult.animals.web;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.AnimalImpl;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AnimalsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private Animal animal = new AnimalImpl("Tom", "Bob cat", Group.MAMMALS, "color", "type");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

    @Test
    public void createShouldWork() throws Exception {
        ResponseEntity<AnimalImpl> response = template.postForEntity(base.toString(), animal, AnimalImpl.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(animal.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(animal.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(animal.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(5);
    }

    @Test
    public void deleteShouldWork() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        JsonNode items = template.getForObject(base.toString(), JsonNode.class);
		List<AnimalImpl> animals = objectMapper.convertValue(items, new TypeReference<List<AnimalImpl>>() {});
        assertThat(animals.size()).isGreaterThanOrEqualTo(5);
		template.delete(base.toString(), animals.get(0));
		JsonNode afterDeleteItems = template.getForObject(base.toString(), JsonNode.class);
		assertThat(afterDeleteItems.size()).isGreaterThanOrEqualTo(4);
    }

    @Test
    public void getShouldWork() throws Exception {
        Animal created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    Animal create(String name) {
        Animal created = template.postForObject(base.toString(), new AnimalImpl(name, name, Group.MAMMALS, "color", "type"), AnimalImpl.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(name);
        return created;
    }
}
