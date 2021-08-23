package cx.catapult.animals.repository;

import static cx.catapult.animals.utils.CatsTestHelper.verifyResult;
import static cx.catapult.animals.utils.CatsTestHelper.verifyResults;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.entity.CatEntity;
import cx.catapult.animals.exception.AnimalNotFoundException;
import cx.catapult.animals.mapper.CatsMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(MockitoExtension.class)
class CatsRepositoryTest {

  private static final String ID = "ff8080817b6fd27e017b6fd282bb0000";
  private static final String COULD_NOT_FIND = "Could not find the animal with id %s";
  private CatsRepository classUnderTest;
  @Mock
  private CatsCrudRepository mockCatsCrudRepository;
  private CatsMapper catsMapper;
  private PodamFactory podamFactory;
  private static final String ERROR_MESSAGE = "No records found";
  private static final String UNABLE_TO_SAVE_RECORDS = "Unable to save records";

  @BeforeEach
  void setUp() {
    catsMapper = getMapper(CatsMapper.class);
    classUnderTest = new CatsRepository(mockCatsCrudRepository, catsMapper);
    podamFactory = new PodamFactoryImpl();
  }

  @Test
  void getCats_happyPath_recordsFetched() {
    final List<CatEntity> expectedCats = podamFactory.manufacturePojo(List.class, CatEntity.class);
    when(mockCatsCrudRepository.findAll()).thenReturn(expectedCats);
    final List<Cat> actualCats = classUnderTest.getCats();
    verifyResults(actualCats, expectedCats);
    verify(mockCatsCrudRepository).findAll();
  }

  @Test
  void getCats_sadPath_unableToFetchRecords() {
    when(mockCatsCrudRepository.findAll()).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.getCats()).isInstanceOf(RuntimeException.class)
                                                      .hasMessage(ERROR_MESSAGE);
    verify(mockCatsCrudRepository).findAll();
  }

  @Test
  void save_happyPath_recordCreated() {
    final CatEntity expectedCat = podamFactory.manufacturePojo(CatEntity.class);
    final Cat cat = podamFactory.manufacturePojo(Cat.class);
    final CatEntity CatEntity = catsMapper.getCatEntity(cat);
    when(mockCatsCrudRepository.save(CatEntity)).thenReturn(expectedCat);
    final Cat actualCat = classUnderTest.save(cat);
    verifyResult(actualCat, expectedCat);
    verify(mockCatsCrudRepository).save(CatEntity);
  }

  @Test
  void save_sadPath_unableToCreateRecord() {
    final Cat cat = podamFactory.manufacturePojo(Cat.class);
    final CatEntity CatEntity = catsMapper.getCatEntity(cat);
    when(mockCatsCrudRepository.save(CatEntity)).thenThrow(new RuntimeException(UNABLE_TO_SAVE_RECORDS));
    assertThatThrownBy(() -> classUnderTest.save(cat)).isInstanceOf(RuntimeException.class)
                                                      .hasMessage(UNABLE_TO_SAVE_RECORDS);
    verify(mockCatsCrudRepository).save(CatEntity);
  }

  @Test
  void getCat_happyPath_recordFetched() {
    final CatEntity expectedCat = podamFactory.manufacturePojo(CatEntity.class);
    when(mockCatsCrudRepository.findById(ID)).thenReturn(of(expectedCat));
    final Cat actualCat = classUnderTest.getCat(ID);
    verifyResult(actualCat, expectedCat);
    verify(mockCatsCrudRepository).findById(ID);
  }

  @Test
  void getCat_sadPath_unableToFetchRecord() {
    when(mockCatsCrudRepository.findById(ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.getCat(ID)).isInstanceOf(RuntimeException.class)
                                                       .hasMessage(ERROR_MESSAGE);
    verify(mockCatsCrudRepository).findById(ID);
  }

  @Test
  void getBird_sadPath_unableToFetchRecord() {
    when(mockCatsCrudRepository.findById(ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.getCat(ID)).isInstanceOf(RuntimeException.class)
                                                       .hasMessage(ERROR_MESSAGE);
    verify(mockCatsCrudRepository).findById(ID);
  }

  @Test
  void getBird_sadPath_unableToFetchRecordReturnEmpty() {
    when(mockCatsCrudRepository.findById(ID)).thenReturn(empty());
    assertThatThrownBy(() -> classUnderTest.getCat(ID)).isInstanceOf(AnimalNotFoundException.class)
                                                       .hasMessage(format(COULD_NOT_FIND, ID));
    verify(mockCatsCrudRepository).findById(ID);
  }

  @Test
  void update_happyPath_recordUpdated() {
    final CatEntity expectedCat = podamFactory.manufacturePojo(CatEntity.class);
    when(mockCatsCrudRepository.findById(ID)).thenReturn(of(expectedCat));
    final Cat cat = podamFactory.manufacturePojo(Cat.class);
    final CatEntity catEntity = catsMapper.getCatEntity(cat);
    catEntity.setId(expectedCat.getId());
    when(mockCatsCrudRepository.save(catEntity)).thenReturn(expectedCat);
    final Cat actualCat = classUnderTest.update(ID, cat);
    verifyResult(actualCat, expectedCat);
    verify(mockCatsCrudRepository).findById(ID);
    verify(mockCatsCrudRepository).save(expectedCat);
  }

  @Test
  void update_sadPath_unableToUpdateRecord() {
    final Cat cat = podamFactory.manufacturePojo(Cat.class);
    when(mockCatsCrudRepository.findById(ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.update(ID, cat)).isInstanceOf(RuntimeException.class)
                                                            .hasMessage(ERROR_MESSAGE);
    verify(mockCatsCrudRepository).findById(ID);
  }

  @Test
  void update_sadPath_unableToUpdateRecordReturnEmpty() {
    final Cat cat = podamFactory.manufacturePojo(Cat.class);
    when(mockCatsCrudRepository.findById(ID)).thenReturn(empty());
    assertThatThrownBy(() -> classUnderTest.update(ID, cat)).isInstanceOf(AnimalNotFoundException.class)
                                                            .hasMessage(format(COULD_NOT_FIND, ID));
    verify(mockCatsCrudRepository).findById(ID);
  }

  @Test
  void delete_happyPath_recordDeleted() {
    final CatEntity expectedCat = podamFactory.manufacturePojo(CatEntity.class);
    when(mockCatsCrudRepository.findById(ID)).thenReturn(of(expectedCat));
    classUnderTest.delete(ID);
    verify(mockCatsCrudRepository).findById(ID);
    verify(mockCatsCrudRepository).delete(expectedCat);
  }

  @Test
  void delete_sadPath_unableToDeleteRecords() {
    when(mockCatsCrudRepository.findById(ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.delete(ID)).isInstanceOf(RuntimeException.class)
                                                       .hasMessage(ERROR_MESSAGE);
    verify(mockCatsCrudRepository).findById(ID);
  }

  @Test
  void delete_sadPath_unableToDeleteRecordsReturnEmpty() {
    when(mockCatsCrudRepository.findById(ID)).thenReturn(empty());
    assertThatThrownBy(() -> classUnderTest.delete(ID)).isInstanceOf(AnimalNotFoundException.class)
                                                       .hasMessage(format(COULD_NOT_FIND, ID));
    verify(mockCatsCrudRepository).findById(ID);
  }

  @Test
  void saveAll_happyPath_recordsSaved() {
    final List<Cat> cats = podamFactory.manufacturePojo(List.class, Cat.class);
    final List<CatEntity> expectedCats = catsMapper.getCatEntities(cats);
    classUnderTest.saveAll(cats);
    verify(mockCatsCrudRepository).saveAll(expectedCats);
  }

  @Test
  void saveAll_sadPath_unableToSaveRecords() {
    final List<Cat> cats = podamFactory.manufacturePojo(List.class, Cat.class);
    final List<CatEntity> expectedCats = catsMapper.getCatEntities(cats);
    doThrow(new RuntimeException(UNABLE_TO_SAVE_RECORDS)).when(mockCatsCrudRepository)
                                                         .saveAll(expectedCats);
    assertThatThrownBy(() -> classUnderTest.saveAll(cats)).isInstanceOf(RuntimeException.class)
                                                          .hasMessage(UNABLE_TO_SAVE_RECORDS);
    verify(mockCatsCrudRepository).saveAll(expectedCats);
  }
}