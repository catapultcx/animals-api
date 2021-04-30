package cx.catapult.animals.tools;

import cx.catapult.animals.api.request.CreateOrUpdateCrustaceanRequest;
import cx.catapult.animals.domain.PersistentCrustacean;

import java.util.UUID;

import static cx.catapult.animals.domain.Group.INVERTEBRATE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class CrustaceanTestTools {

  public static CreateOrUpdateCrustaceanRequest createOrUpdateCrustaceanRequest() {
    return CreateOrUpdateCrustaceanRequest.builder()
        .name(randomAlphabetic(25))
        .description(randomAlphabetic(50))
        .build();
  }

  public static PersistentCrustacean createPersistentCrustacean() {
    return PersistentCrustacean.builder()
        .id(UUID.randomUUID().toString())
        .name(randomAlphabetic(25))
        .description(randomAlphabetic(50))
        .group(INVERTEBRATE)
        .build();
  }
}
