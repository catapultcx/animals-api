package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Crustacean;
import java.net.URL;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseControllerIT<T extends Animal> {

    @LocalServerPort
    protected int port;

    protected URL base;

    protected T animal;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void createShouldWork() {
        ResponseEntity<Crustacean> response = template.postForEntity(base.toString(),
                                                                     animal,
                                                                     Crustacean.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(animal.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(animal.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(animal.getGroup());
    }

    @Test
    public void allShouldWork() {
        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(3);
    }

    @Test
    public void getShouldWork() {
        T created = create("Test 1");
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

     abstract T create(String name);
}
