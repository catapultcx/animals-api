package cx.catapult.animals.web;

import cx.catapult.animals.api.CreateCrustaceanRequest;
import cx.catapult.animals.api.response.Crustacean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static cx.catapult.animals.domain.Group.INVERTEBRATE;
import static cx.catapult.animals.tools.CrustaceanTestTools.createCrustaceanRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrustaceanControllerIT extends BaseIT {

  @Test
  public void shouldCreateACrustacean() {
    CreateCrustaceanRequest payload = createCrustaceanRequest();

    ResponseEntity<Crustacean> response = submitCreateRequest(payload);

    assertThat(response.getStatusCode()).isEqualTo(CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isNotNull();
    assertThat(response.getBody())
        .extracting("name", "description", "group")
        .contains(payload.getName(), payload.getDescription(), INVERTEBRATE);
  }

  @Test
  public void shouldReturnAllCrustaceans() {
    String createdId1 = submitCreateRequest(createCrustaceanRequest()).getBody().getId();
    String createdId2 = submitCreateRequest(createCrustaceanRequest()).getBody().getId();

    HttpEntity<CreateCrustaceanRequest> request = new HttpEntity<>(new HttpHeaders());

    ResponseEntity<List<Crustacean>> response =
        restTemplate.exchange(
            "/api/1/crustaceans",
            GET,
            request,
            new ParameterizedTypeReference<List<Crustacean>>() {});

    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).hasSize(2);
    assertThat(response.getBody())
        .extracting(Crustacean::getId)
        .containsExactly(createdId1, createdId2);
  }

  @Test
  public void shouldReturnACrustaceanById() {
    String createdId = submitCreateRequest(createCrustaceanRequest()).getBody().getId();

    HttpEntity<CreateCrustaceanRequest> request = new HttpEntity<>(new HttpHeaders());

    ResponseEntity<Crustacean> response =
        restTemplate.exchange(
            String.format("/api/1/crustaceans/%s", createdId),
            GET,
            request,
            new ParameterizedTypeReference<Crustacean>() {});

    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isEqualTo(createdId);
  }

  private ResponseEntity<Crustacean> submitCreateRequest(
      CreateCrustaceanRequest createCrustaceanRequest) {
    HttpEntity<CreateCrustaceanRequest> request =
        new HttpEntity<>(createCrustaceanRequest, new HttpHeaders());
    return restTemplate.exchange(
        "/api/1/crustaceans", POST, request, new ParameterizedTypeReference<Crustacean>() {});
  }
}
