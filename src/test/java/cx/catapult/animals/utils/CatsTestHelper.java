package cx.catapult.animals.utils;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.entity.CatEntity;
import java.util.List;

public class CatsTestHelper {

  public static void verifyResults(final List<Cat> expectedCats, final List<CatEntity> actualCats) {
    range(0, expectedCats.size())
        .forEach(counter -> CatsTestHelper.verifyResult(expectedCats.get(counter), actualCats.get(counter)));
  }

  public static void verifyResult(final Cat expectedCat, final CatEntity actualCat) {
    assertThat(actualCat.getName()).isEqualTo(expectedCat.getName());
    assertThat(actualCat.getDescription()).isEqualTo(expectedCat.getDescription());
    assertThat(actualCat.getId()).isEqualTo(expectedCat.getId());
  }
}
