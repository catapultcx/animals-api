package cx.catapult.animals.web;

import cx.catapult.animals.api.request.CreateOrUpdateCrustaceanRequest;
import cx.catapult.animals.api.response.Crustacean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static cx.catapult.animals.domain.Group.INVERTEBRATE;
import static cx.catapult.animals.tools.CrustaceanTestTools.createOrUpdateCrustaceanRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrustaceanControllerIT extends BaseIT {

  @Test
  public void shouldCreateACrustacean() {
    CreateOrUpdateCrustaceanRequest payload = createOrUpdateCrustaceanRequest();

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
    String createdId1 = submitCreateRequest(createOrUpdateCrustaceanRequest()).getBody().getId();
    String createdId2 = submitCreateRequest(createOrUpdateCrustaceanRequest()).getBody().getId();

    ResponseEntity<Crustacean[]> response =
        restTemplate.getForEntity("/api/1/crustaceans", Crustacean[].class);

    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).hasSize(2);
    assertThat(response.getBody())
        .extracting(Crustacean::getId)
        .containsExactly(createdId1, createdId2);
  }

  @Test
  public void shouldReturnACrustaceanById() {
    String createdId = submitCreateRequest(createOrUpdateCrustaceanRequest()).getBody().getId();

    ResponseEntity<Crustacean> response =
        restTemplate.getForEntity(
            String.format("/api/1/crustaceans/%s", createdId), Crustacean.class);

    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isEqualTo(createdId);
  }

  @Test
  public void shouldReturnNotFoundStatusCodeWhenGetCrustaceanWithIdWhichDoesNotExist() {
    ResponseEntity<String> response =
        restTemplate.getForEntity(
            String.format("/api/1/crustaceans/%s", "nonexistent"), String.class);

    assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    assertThat(response.getBody()).isEqualTo("Crustacean by 'nonexistent' was not found");
  }

  @Test
  public void shouldReturnNotFoundStatusCodeWhenDeleteCrustaceanWithIdWhichDoesNotExist() {
    HttpEntity<CreateOrUpdateCrustaceanRequest> request = new HttpEntity<>(new HttpHeaders());

    ResponseEntity deleteResponse =
        restTemplate.exchange(
            String.format("/api/1/crustaceans/%s", "nonexistent"), DELETE, request, String.class);

    assertThat(deleteResponse.getStatusCode()).isEqualTo(NOT_FOUND);
    assertThat(deleteResponse.getBody()).isEqualTo("Crustacean by 'nonexistent' was not found");
  }

  private ResponseEntity<Crustacean> submitCreateRequest(
      CreateOrUpdateCrustaceanRequest createCrustaceanRequest) {
    HttpEntity<CreateOrUpdateCrustaceanRequest> request =
        new HttpEntity<>(createCrustaceanRequest, new HttpHeaders());
    return restTemplate.exchange(
        "/api/1/crustaceans", POST, request, new ParameterizedTypeReference<Crustacean>() {});
  }

  @Test
  public void shouldReturnUpdatedCrustaceanById() {
    String createdId = submitCreateRequest(createOrUpdateCrustaceanRequest()).getBody().getId();

    CreateOrUpdateCrustaceanRequest payload = createOrUpdateCrustaceanRequest();

    HttpEntity<CreateOrUpdateCrustaceanRequest> request =
        new HttpEntity<>(payload, new HttpHeaders());

    ResponseEntity<Crustacean> response =
        restTemplate.exchange(
            String.format("/api/1/crustaceans/%s", createdId),
            POST,
            request,
            new ParameterizedTypeReference<Crustacean>() {});

    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody())
        .extracting("id", "name", "description", "group")
        .contains(createdId, payload.getName(), payload.getDescription());
  }

  @Test
  public void shouldReturnUpdatedCrustaceanByIdWhichDoesNotExist() {
    CreateOrUpdateCrustaceanRequest payload = createOrUpdateCrustaceanRequest();

    HttpEntity<CreateOrUpdateCrustaceanRequest> request =
        new HttpEntity<>(payload, new HttpHeaders());

    ResponseEntity<Crustacean> response =
        restTemplate.exchange(
            String.format("/api/1/crustaceans/%s", "nonexistent"),
            POST,
            request,
            new ParameterizedTypeReference<Crustacean>() {});

    assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    assertThat(response.getBody()).isEqualTo("Crustacean by 'nonexistent' was not found");
  }
}
