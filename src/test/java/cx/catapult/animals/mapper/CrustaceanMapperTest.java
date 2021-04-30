package cx.catapult.animals.mapper;

import cx.catapult.animals.api.request.CreateOrUpdateCrustaceanRequest;
import cx.catapult.animals.api.response.Crustacean;
import cx.catapult.animals.domain.PersistentCrustacean;
import org.junit.jupiter.api.Test;

import static cx.catapult.animals.domain.Group.INVERTEBRATE;
import static org.assertj.core.api.Assertions.assertThat;

class CrustaceanMapperTest {

  CrustaceanMapper crustaceanMapper = new CrustaceanMapperImpl();

  @Test
  public void shouldReturnNullIfPersistentCrustaceanIsNull() {
    assertThat(crustaceanMapper.toPersistentCrustacean(null)).isNull();
  }

  @Test
  public void shouldMapAllCreateCrustaceanPropertiesToPersistentCrustacean() {
    CreateOrUpdateCrustaceanRequest createCrustaceanRequest =
        CreateOrUpdateCrustaceanRequest.builder().name("Some Name").description("Some Description").build();

    PersistentCrustacean persistentCrustacean =
        crustaceanMapper.toPersistentCrustacean(createCrustaceanRequest);

    assertThat(persistentCrustacean)
        .extracting("name", "description", "group")
        .contains(
            createCrustaceanRequest.getName(),
            createCrustaceanRequest.getDescription(),
            INVERTEBRATE);
  }

  @Test
  public void shouldReturnNullIfCrustaceanIsNull() {
    assertThat(crustaceanMapper.toCrustacean(null)).isNull();
  }

  @Test
  public void shouldMapAllPersistentCrustaceanPropertiesToCrustacean() {
    PersistentCrustacean persistentCrustacean =
        PersistentCrustacean.builder()
            .name("Some Name")
            .description("Some Description")
            .group(INVERTEBRATE)
            .build();

    Crustacean crustacean = crustaceanMapper.toCrustacean(persistentCrustacean);

    assertThat(crustacean)
        .extracting("name", "description", "group")
        .contains(
            persistentCrustacean.getName(),
            persistentCrustacean.getDescription(),
            persistentCrustacean.getGroup());
  }
}
