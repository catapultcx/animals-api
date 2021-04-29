package cx.catapult.animals.web;

import cx.catapult.animals.api.CreateCrustaceanRequest;
import cx.catapult.animals.mapper.CrustaceanMapper;
import cx.catapult.animals.repository.CrustaceanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CrustaceansController.class)
public class CrustaceansControllerTest extends BaseControllerTest {

  @MockBean private CrustaceanRepository crustaceanRepository;
  @MockBean private CrustaceanMapper crustaceanMapper;

  CreateCrustaceanRequest payload =
      CreateCrustaceanRequest.builder()
          .name(randomAlphabetic(25))
          .description(randomAlphabetic(50))
          .build();

  @Test
  public void shouldReturnCreatedResponseStatus() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.post("/api/1/crustaceans")
                .content(toJson(payload))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldReturnBadRequestResponseStatusDueToNameTooLong() throws Exception {
    CreateCrustaceanRequest invalidPayload = payload.toBuilder().name(randomAlphabetic(26)).build();

    mvc.perform(
            MockMvcRequestBuilders.post("/api/1/crustaceans")
                .content(toJson(invalidPayload))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestResponseStatusDueToDescriptionTooLong() throws Exception {
    CreateCrustaceanRequest invalidPayload =
        payload.toBuilder().description(randomAlphabetic(51)).build();

    mvc.perform(
            MockMvcRequestBuilders.post("/api/1/crustaceans")
                .content(toJson(invalidPayload))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }
}
