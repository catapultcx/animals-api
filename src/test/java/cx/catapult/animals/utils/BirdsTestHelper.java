package cx.catapult.animals.utils;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.entity.BirdEntity;
import java.util.List;

public class BirdsTestHelper {

  public static void verifyResults(final List<Bird> expectedBirds, final List<BirdEntity> actualBirds) {
    range(0, expectedBirds.size())
        .forEach(counter -> BirdsTestHelper.verifyResult(expectedBirds.get(counter), actualBirds.get(counter)));
  }

  public static void verifyResult(final Bird expectedBird, final BirdEntity actualBird) {
    assertThat(actualBird.getName()).isEqualTo(expectedBird.getName());
    assertThat(actualBird.getDescription()).isEqualTo(expectedBird.getDescription());
    assertThat(actualBird.getId()).isEqualTo(expectedBird.getId());
  }
}
