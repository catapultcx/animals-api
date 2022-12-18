package cx.catapult.animals.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import cx.catapult.animals.service.AnimalsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AnimalsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    @SpyBean
    private AnimalsService animalsService;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals");
    }

    @Test
    public void canGetAll() {
        when(animalsService.all()).thenReturn(Set.of(new BaseAnimal("Bob","Awkward", Group.MAMMALS,"Gerbil", "Beige"),
                new BaseAnimal("Colin","gauche", Group.MAMMALS,"Guinea pig", "Black")));

        Collection items = template.getForObject(base.toString(), Collection.class);
        assertThat(items).extracting("name").containsExactlyInAnyOrder("Bob", "Colin");
        assertThat(items).extracting("type").containsExactlyInAnyOrder("Gerbil", "Guinea pig");
    }

    @Test
    public void canGetSpecificAnimal() throws Exception {
        BaseAnimal bob = new BaseAnimal("666","Bob", "Awkward", Group.MAMMALS, "Gerbil", "Beige");
        String expected = objectMapper.writeValueAsString(bob);
        when(animalsService.get("666")).thenReturn(bob);
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + bob.getId(), String.class);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    public void canCreateAnimal() {
        BaseAnimal bob = new BaseAnimal("666","Bob", "Awkward", Group.MAMMALS, "Gerbil", "Beige");

        ResponseEntity<BaseAnimal> response = template.postForEntity(base.toString(), bob, BaseAnimal.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(bob.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(bob.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(bob.getGroup());
        assertThat(response.getBody().getType()).isEqualTo(bob.getType());
        assertThat(response.getBody().getColour()).isEqualTo(bob.getColour());
    }

}
