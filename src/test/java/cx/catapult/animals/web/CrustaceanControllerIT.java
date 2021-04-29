package cx.catapult.animals.web;

import cx.catapult.animals.api.CreateCrustaceanRequest;
import cx.catapult.animals.api.response.Crustacean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static cx.catapult.animals.domain.Group.INVERTEBRATE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrustaceanControllerIT extends BaseIT {

  @Test
  public void shouldCreateACrustacean() {
    CreateCrustaceanRequest payload =
        CreateCrustaceanRequest.builder()
            .name(randomAlphabetic(25))
            .description(randomAlphabetic(50))
            .build();

    final HttpEntity<CreateCrustaceanRequest> request =
        new HttpEntity<>(payload, new HttpHeaders());
    final ResponseEntity<Crustacean> response =
        restTemplate.exchange(
            "/api/1/crustaceans", POST, request, new ParameterizedTypeReference<Crustacean>() {});

    assertThat(response.getStatusCode()).isEqualTo(CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isNotNull();
    assertThat(response.getBody())
        .extracting("name", "description", "group")
        .contains(payload.getName(), payload.getDescription(), INVERTEBRATE);
  }
}
