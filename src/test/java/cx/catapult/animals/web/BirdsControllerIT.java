package cx.catapult.animals.web;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import cx.catapult.animals.domain.Bird;
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

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BirdsControllerIT {

  private static final String BIRD_NAME = "Test 1";
  @LocalServerPort
  private int port;

  private URL base;

  private final Bird bird = new Bird("Barn owl", "With bright white feathers and a distinctive heart-shaped face");

  @Autowired
  private TestRestTemplate template;

  @BeforeEach
  void setUp() throws Exception {
    this.base = new URL(format("http://localhost:%s%s", port, "/api/1/birds"));
  }

  @Test
  void createShouldWork() {
    final ResponseEntity<Bird> response = template.postForEntity(base.toString(), bird, Bird.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()
                       .getId()).isNotEmpty();
    assertThat(response.getBody()
                       .getName()).isEqualTo(bird.getName());
    assertThat(response.getBody()
                       .getDescription()).isEqualTo(bird.getDescription());
    assertThat(response.getBody()
                       .getGroup()).isEqualTo(bird.getGroup());
  }

  @Test
  void allShouldWork() {
    final Collection items = template.getForObject(base.toString(), Collection.class);
    assertThat(items).hasSizeGreaterThanOrEqualTo(7);
  }

  @Test
  void getShouldWork() {
    final Bird created = create(BIRD_NAME);
    final ResponseEntity<String> response = template.getForEntity(getFormattedUrl(created), String.class);
    assertThat(response.getBody()).isNotEmpty();
  }

  @Test
  void deleteShouldWork() {
    final Bird created = create(BIRD_NAME);
    template.delete(getFormattedUrl(created), String.class);
  }

  private String getFormattedUrl(final Bird created) {
    return format("%s/%s", base.toString(), created.getId());
  }

  private Bird create(final String name) {
    final Bird created = template.postForObject(base.toString(), new Bird(name, name), Bird.class);
    assertThat(created.getId()).isNotEmpty();
    assertThat(created.getName()).isEqualTo(name);
    return created;
  }
}
