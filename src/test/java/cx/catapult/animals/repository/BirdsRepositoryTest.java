package cx.catapult.animals.repository;

import static cx.catapult.animals.utils.BirdsTestHelper.verifyResult;
import static cx.catapult.animals.utils.BirdsTestHelper.verifyResults;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.entity.BirdEntity;
import cx.catapult.animals.exception.AnimalNotFoundException;
import cx.catapult.animals.mapper.BirdsMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(MockitoExtension.class)
class BirdsRepositoryTest {

  private static final String ID = "ff8080817b6fd27e017b6fd282bb0000";
  private static final String COULD_NOT_FIND = "Could not find the animal with id %s";
  private BirdsRepository classUnderTest;
  @Mock
  private BirdsCrudRepository mockBirdsCrudRepository;
  private BirdsMapper birdsMapper;
  private PodamFactory podamFactory;
  private static final String ERROR_MESSAGE = "No records found";
  private static final String UNABLE_TO_SAVE_RECORDS = "Unable to save records";

  @BeforeEach
  void setUp() {
    birdsMapper = getMapper(BirdsMapper.class);
    classUnderTest = new BirdsRepository(mockBirdsCrudRepository, birdsMapper);
    podamFactory = new PodamFactoryImpl();
  }

  @Test
  void getBirds_happyPath_recordsFetched() {
    final List<BirdEntity> expectedBirds = podamFactory.manufacturePojo(List.class, BirdEntity.class);
    when(mockBirdsCrudRepository.findAll()).thenReturn(expectedBirds);
    final List<Bird> actualBirds = classUnderTest.getBirds();
    verifyResults(actualBirds, expectedBirds);
    verify(mockBirdsCrudRepository).findAll();
  }

  @Test
  void getBirds_sadPath_unableToFetchRecords() {
    when(mockBirdsCrudRepository.findAll()).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.getBirds()).isInstanceOf(RuntimeException.class)
                                                       .hasMessage(ERROR_MESSAGE);
    verify(mockBirdsCrudRepository).findAll();
  }

  @Test
  void save_happyPath_recordCreated() {
    final BirdEntity expectedBird = podamFactory.manufacturePojo(BirdEntity.class);
    final Bird bird = podamFactory.manufacturePojo(Bird.class);
    final BirdEntity birdEntity = birdsMapper.getBirdEntity(bird);
    when(mockBirdsCrudRepository.save(birdEntity)).thenReturn(expectedBird);
    final Bird actualBird = classUnderTest.save(bird);
    verifyResult(actualBird, expectedBird);
    verify(mockBirdsCrudRepository).save(birdEntity);
  }

  @Test
  void save_sadPath_unableToCreateRecord() {
    final Bird bird = podamFactory.manufacturePojo(Bird.class);
    final BirdEntity birdEntity = birdsMapper.getBirdEntity(bird);
    when(mockBirdsCrudRepository.save(birdEntity)).thenThrow(new RuntimeException(UNABLE_TO_SAVE_RECORDS));
    assertThatThrownBy(() -> classUnderTest.save(bird)).isInstanceOf(RuntimeException.class)
                                                       .hasMessage(UNABLE_TO_SAVE_RECORDS);
    verify(mockBirdsCrudRepository).save(birdEntity);
  }

  @Test
  void getBird_happyPath_recordFetched() {
    final BirdEntity expectedBird = podamFactory.manufacturePojo(BirdEntity.class);
    when(mockBirdsCrudRepository.findById(ID)).thenReturn(of(expectedBird));
    final Bird actualBird = classUnderTest.getBird(ID);
    verifyResult(actualBird, expectedBird);
    verify(mockBirdsCrudRepository).findById(ID);
  }

  @Test
  void getBird_sadPath_unableToFetchRecord() {
    when(mockBirdsCrudRepository.findById(ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.getBird(ID)).isInstanceOf(RuntimeException.class)
                                                        .hasMessage(ERROR_MESSAGE);
    verify(mockBirdsCrudRepository).findById(ID);
  }

  @Test
  void getBird_sadPath_unableToFetchRecordReturnEmpty() {
    when(mockBirdsCrudRepository.findById(ID)).thenReturn(empty());
    assertThatThrownBy(() -> classUnderTest.getBird(ID)).isInstanceOf(AnimalNotFoundException.class)
                                                        .hasMessage(format(COULD_NOT_FIND, ID));
    verify(mockBirdsCrudRepository).findById(ID);
  }

  @Test
  void update_happyPath_recordUpdated() {
    final BirdEntity expectedBird = podamFactory.manufacturePojo(BirdEntity.class);
    when(mockBirdsCrudRepository.findById(ID)).thenReturn(of(expectedBird));
    final Bird bird = podamFactory.manufacturePojo(Bird.class);
    final BirdEntity birdEntity = birdsMapper.getBirdEntity(bird);
    birdEntity.setId(expectedBird.getId());
    when(mockBirdsCrudRepository.save(birdEntity)).thenReturn(expectedBird);
    final Bird actualBird = classUnderTest.update(ID, bird);
    verifyResult(actualBird, expectedBird);
    verify(mockBirdsCrudRepository).findById(ID);
    verify(mockBirdsCrudRepository).save(expectedBird);
  }

  @Test
  void update_sadPath_unableToUpdateRecord() {
    when(mockBirdsCrudRepository.findById(ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.getBird(ID)).isInstanceOf(RuntimeException.class)
                                                        .hasMessage(ERROR_MESSAGE);
    verify(mockBirdsCrudRepository).findById(ID);
  }

  @Test
  void update_sadPath_unableToUpdateRecordReturnEmpty() {
    final Bird bird = podamFactory.manufacturePojo(Bird.class);
    when(mockBirdsCrudRepository.findById(ID)).thenReturn(empty());
    assertThatThrownBy(() -> classUnderTest.update(ID, bird)).isInstanceOf(AnimalNotFoundException.class)
                                                             .hasMessage(format(COULD_NOT_FIND, ID));
    verify(mockBirdsCrudRepository).findById(ID);
  }

  @Test
  void delete_happyPath_recordDeleted() {
    final BirdEntity expectedBird = podamFactory.manufacturePojo(BirdEntity.class);
    when(mockBirdsCrudRepository.findById(ID)).thenReturn(of(expectedBird));
    classUnderTest.delete(ID);
    verify(mockBirdsCrudRepository).findById(ID);
    verify(mockBirdsCrudRepository).delete(expectedBird);
  }

  @Test
  void delete_sadPath_unableToDeleteRecordsReturnEmpty() {
    when(mockBirdsCrudRepository.findById(ID)).thenReturn(empty());
    assertThatThrownBy(() -> classUnderTest.delete(ID)).isInstanceOf(AnimalNotFoundException.class)
                                                       .hasMessage(format(COULD_NOT_FIND, ID));
    verify(mockBirdsCrudRepository).findById(ID);
  }

  @Test
  void delete_sadPath_unableToDeleteRecords() {
    when(mockBirdsCrudRepository.findById(ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.delete(ID)).isInstanceOf(RuntimeException.class)
                                                       .hasMessage(ERROR_MESSAGE);
    verify(mockBirdsCrudRepository).findById(ID);
  }

  @Test
  void saveAll_happyPath_recordsSaved() {
    final List<Bird> birds = podamFactory.manufacturePojo(List.class, Bird.class);
    final List<BirdEntity> expectedBirds = birdsMapper.getBirdEntities(birds);
    classUnderTest.saveAll(birds);
    verify(mockBirdsCrudRepository).saveAll(expectedBirds);
  }

  @Test
  void saveAll_sadPath_unableToSaveRecords() {
    final List<Bird> birds = podamFactory.manufacturePojo(List.class, Bird.class);
    final List<BirdEntity> expectedBirds = birdsMapper.getBirdEntities(birds);
    doThrow(new RuntimeException(UNABLE_TO_SAVE_RECORDS)).when(mockBirdsCrudRepository)
                                                         .saveAll(expectedBirds);
    assertThatThrownBy(() -> classUnderTest.saveAll(birds)).isInstanceOf(RuntimeException.class)
                                                           .hasMessage(UNABLE_TO_SAVE_RECORDS);
    verify(mockBirdsCrudRepository).saveAll(expectedBirds);
  }
}