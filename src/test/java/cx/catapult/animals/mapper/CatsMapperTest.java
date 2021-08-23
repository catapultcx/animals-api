package cx.catapult.animals.mapper;

import static cx.catapult.animals.utils.CatsTestHelper.verifyResult;
import static cx.catapult.animals.utils.CatsTestHelper.verifyResults;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.entity.CatEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class CatsMapperTest {

  private CatsMapper classUnderTest;
  private PodamFactory podamFactory;

  @BeforeEach
  void setUp() {
    classUnderTest = getMapper(CatsMapper.class);
    podamFactory = new PodamFactoryImpl();
  }

  @Test
  void getCatEntities_happyPath_mappedCorrectly() {
    final List<Cat> expectedCats = podamFactory.manufacturePojo(List.class, Cat.class);
    final List<CatEntity> actualCats = classUnderTest.getCatEntities(expectedCats);
    verifyResults(expectedCats, actualCats);
  }

  @Test
  void getCatEntities_sadPath_notMappedCorrectly() {
    assertThat(classUnderTest.getCatEntities(null)).isNull();
  }

  @Test
  void getCatEntity_happyPath_mappedCorrectly() {
    final Cat expectedCat = podamFactory.manufacturePojo(Cat.class);
    final CatEntity actualCat = classUnderTest.getCatEntity(expectedCat);
    verifyResult(expectedCat, actualCat);
  }

  @Test
  void getCatEntity_sadPath_notMappedCorrectly() {
    assertThat(classUnderTest.getCatEntity(null)).isNull();
  }

  @Test
  void getCats_happyPath_mappedCorrectly() {
    final List<CatEntity> expectedCats = podamFactory.manufacturePojo(List.class, CatEntity.class);
    final List<Cat> actualCats = classUnderTest.getCats(expectedCats);
    verifyResults(actualCats, expectedCats);
  }

  @Test
  void getCats_sadPath_notMappedCorrectly() {
    assertThat(classUnderTest.getCats(null)).isNull();
  }

  @Test
  void getCat_happyPath_mappedCorrectly() {
    final CatEntity expectedCat = podamFactory.manufacturePojo(CatEntity.class);
    final Cat actualCat = classUnderTest.getCat(expectedCat);
    verifyResult(actualCat, expectedCat);
  }

  @Test
  void getCat_sadPath_notMappedCorrectly() {
    assertThat(classUnderTest.getCat(null)).isNull();
  }
}