package cx.catapult.animals.mapper;

import static cx.catapult.animals.utils.BirdsTestHelper.verifyResult;
import static cx.catapult.animals.utils.BirdsTestHelper.verifyResults;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.entity.BirdEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class BirdsMapperTest {

  private BirdsMapper classUnderTest;
  private PodamFactory podamFactory;

  @BeforeEach
  void setUp() {
    classUnderTest = getMapper(BirdsMapper.class);
    podamFactory = new PodamFactoryImpl();
  }

  @Test
  void getBirdEntities_happyPath_mappedCorrectly() {
    final List<Bird> expectedBirds = podamFactory.manufacturePojo(List.class, Bird.class);
    final List<BirdEntity> actualBirds = classUnderTest.getBirdEntities(expectedBirds);
    verifyResults(expectedBirds, actualBirds);
  }

  @Test
  void getBirdEntities_sadPath_notMappedCorrectly() {
    assertThat(classUnderTest.getBirdEntities(null)).isNull();
  }

  @Test
  void getBirdEntity_happyPath_mappedCorrectly() {
    final Bird expectedBird = podamFactory.manufacturePojo(Bird.class);
    final BirdEntity actualBird = classUnderTest.getBirdEntity(expectedBird);
    verifyResult(expectedBird, actualBird);
  }

  @Test
  void getBirdEntity_sadPath_notMappedCorrectly() {
    assertThat(classUnderTest.getBirdEntity(null)).isNull();
  }

  @Test
  void getBirds_happyPath_mappedCorrectly() {
    final List<BirdEntity> expectedBirds = podamFactory.manufacturePojo(List.class, BirdEntity.class);
    final List<Bird> actualBirds = classUnderTest.getBirds(expectedBirds);
    verifyResults(actualBirds, expectedBirds);
  }

  @Test
  void getBirds_sadPath_notMappedCorrectly() {
    assertThat(classUnderTest.getBirds(null)).isNull();
  }

  @Test
  void getBird_happyPath_mappedCorrectly() {
    final BirdEntity expectedBird = podamFactory.manufacturePojo(BirdEntity.class);
    final Bird actualBird = classUnderTest.getBird(expectedBird);
    verifyResult(actualBird, expectedBird);
  }

  @Test
  void getBird_sadPath_notMappedCorrectly() {
    assertThat(classUnderTest.getBird(null)).isNull();
  }
}