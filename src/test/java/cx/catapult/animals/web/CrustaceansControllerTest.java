package cx.catapult.animals.web;

import cx.catapult.animals.api.CreateCrustaceanRequest;
import cx.catapult.animals.api.response.Crustacean;
import cx.catapult.animals.domain.PersistentCrustacean;
import cx.catapult.animals.mapper.CrustaceanMapper;
import cx.catapult.animals.repository.CrustaceanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static cx.catapult.animals.tools.CrustaceanTestTools.createPersistentCrustacean;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CrustaceansController.class)
public class CrustaceansControllerTest extends BaseControllerTest {

  private final String URL = "/api/1/crustaceans";

  @MockBean private CrustaceanRepository crustaceanRepository;
  @MockBean private CrustaceanMapper crustaceanMapper;

  CreateCrustaceanRequest payload =
      CreateCrustaceanRequest.builder()
          .name(randomAlphabetic(25))
          .description(randomAlphabetic(50))
          .build();

  @Test
  public void shouldReturnCreatedResponseStatusWhenPostCreateCrustaceanRequest() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.post(URL)
                .content(toJson(payload))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldReturnBadRequestResponseStatusWhenPostCreateCrustaceanRequestWithNameTooLong()
      throws Exception {
    CreateCrustaceanRequest invalidPayload = payload.toBuilder().name(randomAlphabetic(26)).build();

    mvc.perform(
            MockMvcRequestBuilders.post(URL)
                .content(toJson(invalidPayload))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void
      shouldReturnBadRequestResponseStatusWhenPostCreateCrustaceanRequestWithDescriptionTooLong()
          throws Exception {
    CreateCrustaceanRequest invalidPayload =
        payload.toBuilder().description(randomAlphabetic(51)).build();

    mvc.perform(
            MockMvcRequestBuilders.post(URL)
                .content(toJson(invalidPayload))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturnOkResponseStatusWhenGetList() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(URL)).andExpect(status().isOk());
  }

  @Test
  public void shouldReturnOkResponseStatusWhenGetById() throws Exception {
    PersistentCrustacean persistentCrustacean = createPersistentCrustacean();

    given(crustaceanRepository.findById(persistentCrustacean.getId()))
        .willReturn(Optional.of(persistentCrustacean));
    given(crustaceanMapper.toCrustacean(persistentCrustacean))
        .willReturn(
            Crustacean.builder()
                .id(persistentCrustacean.getId())
                .name(persistentCrustacean.getName())
                .description(persistentCrustacean.getDescription())
                .group(persistentCrustacean.getGroup())
                .build());

    mvc.perform(MockMvcRequestBuilders.get(URL + "/" + persistentCrustacean.getId()))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturnNotFoundResponseStatusWhenGetByIdWhichDoesNotExist() throws Exception {
    given(crustaceanRepository.existsById("123")).willReturn(false);

    mvc.perform(MockMvcRequestBuilders.get(URL + "/123")).andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnOkResponseStatusWhenDeleteById() throws Exception {
    given(crustaceanRepository.existsById("123")).willReturn(true);

    mvc.perform(MockMvcRequestBuilders.delete(URL + "/123")).andExpect(status().isOk());
  }

  @Test
  public void shouldReturnOkResponseStatusWhenDeleteByIdWhichDoesNotExist() throws Exception {
    given(crustaceanRepository.existsById("123")).willReturn(false);

    mvc.perform(MockMvcRequestBuilders.delete(URL + "/123")).andExpect(status().isNotFound());
  }
}
