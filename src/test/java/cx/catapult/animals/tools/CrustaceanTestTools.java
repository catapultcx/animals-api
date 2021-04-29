package cx.catapult.animals.tools;

import cx.catapult.animals.api.CreateCrustaceanRequest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class CrustaceanTestTools {

  public static CreateCrustaceanRequest createCrustaceanRequest() {
    return CreateCrustaceanRequest.builder()
        .name(randomAlphabetic(25))
        .description(randomAlphabetic(50))
        .build();
  }
}
