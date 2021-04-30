package cx.catapult.animals.web;

import cx.catapult.animals.api.request.CreateOrUpdateCrustaceanRequest;
import cx.catapult.animals.domain.PersistentCrustacean;
import cx.catapult.animals.mapper.CrustaceanMapperImpl;
import cx.catapult.animals.repository.CrustaceanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static cx.catapult.animals.tools.CrustaceanTestTools.createPersistentCrustacean;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CrustaceansController.class)
public class CrustaceansControllerTest extends BaseControllerTest {

  private final String URL = "/api/1/crustaceans";

  @MockBean private CrustaceanRepository crustaceanRepository;
  @SpyBean private CrustaceanMapperImpl crustaceanMapper;

  CreateOrUpdateCrustaceanRequest createPayload =
      CreateOrUpdateCrustaceanRequest.builder()
          .name(randomAlphabetic(25))
          .description(randomAlphabetic(50))
          .build();

  CreateOrUpdateCrustaceanRequest updatePayload =
      CreateOrUpdateCrustaceanRequest.builder()
          .name(randomAlphabetic(25))
          .description(randomAlphabetic(50))
          .build();

  @Test
  public void shouldReturnCreatedResponseStatusWhenPostCreateCrustaceanRequest() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.post(URL)
                .content(toJson(createPayload))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldReturnBadRequestResponseStatusWhenPostCreateCrustaceanRequestWithNameTooLong()
      throws Exception {
    CreateOrUpdateCrustaceanRequest invalidPayload =
        createPayload.toBuilder().name(randomAlphabetic(26)).build();

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
    CreateOrUpdateCrustaceanRequest invalidPayload =
        createPayload.toBuilder().description(randomAlphabetic(51)).build();

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
    given(crustaceanMapper.toCrustacean(persistentCrustacean)).willCallRealMethod();

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

  @Test
  public void shouldReturnOkResponseStatusWhenPutUpdateCrustaceanRequestById() throws Exception {
    PersistentCrustacean persistentCrustacean = createPersistentCrustacean();

    given(crustaceanRepository.findById(persistentCrustacean.getId()))
        .willReturn(Optional.of(persistentCrustacean));
    given(crustaceanMapper.toPersistentCrustacean(any(CreateOrUpdateCrustaceanRequest.class)))
        .willCallRealMethod();
    given(crustaceanRepository.save(any(PersistentCrustacean.class)))
        .willReturn(
            PersistentCrustacean.builder()
                .id(persistentCrustacean.getId())
                .name(updatePayload.getName())
                .description(updatePayload.getDescription())
                .group(persistentCrustacean.getGroup())
                .build());
    given(crustaceanMapper.toCrustacean(any(PersistentCrustacean.class))).willCallRealMethod();

    mvc.perform(
            MockMvcRequestBuilders.put(URL + "/" + persistentCrustacean.getId())
                .content(toJson(updatePayload))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturnOkResponseStatusWhenPutUpdateCrustaceanRequestByIdWhichDoesNotExist()
      throws Exception {
    PersistentCrustacean persistentCrustacean = createPersistentCrustacean();

    given(crustaceanRepository.findById(persistentCrustacean.getId()))
        .willReturn(Optional.of(persistentCrustacean));

    mvc.perform(
            MockMvcRequestBuilders.put(URL + "/123")
                .content(toJson(updatePayload))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
