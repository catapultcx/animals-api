package cx.catapult.animals.service;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.repository.BirdsRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(MockitoExtension.class)
class BirdsServiceTest {

  private static final String ERROR_MESSAGE = "Unable to fetch records";
  private static final String ID = "ff8080817b6fd27e017b6fd282bb0000";

  @Mock
  private BirdsRepository mockBirdsRepository;
  @InjectMocks
  private BirdsService classUnderTest;
  private List<Bird> expectedBirds;

  @BeforeEach
  void setUp() {
    expectedBirds = new PodamFactoryImpl().manufacturePojo(List.class, Bird.class);
  }

  @Test
  void initialize_happyPath_manageToInitialize() {
    classUnderTest.initialize();
    verify(mockBirdsRepository).saveAll(anyList());
  }

  @Test
  void initialize_sadPath_notAbleToInitialize() {
    doThrow(RuntimeException.class).when(mockBirdsRepository)
                                   .saveAll(anyList());
    assertThatThrownBy(() -> classUnderTest.initialize())
        .isInstanceOf(RuntimeException.class);
    verify(mockBirdsRepository).saveAll(anyList());
  }

  @Test
  void all_happyPath_fetchedAllTheRecords() {
    when(mockBirdsRepository.getBirds()).thenReturn(expectedBirds);
    final List<Bird> actualBirds = (List<Bird>) classUnderTest.all();
    range(0, expectedBirds.size())
        .forEach(counter -> verifyResult(expectedBirds.get(counter), actualBirds.get(counter)));
    verify(mockBirdsRepository).getBirds();
  }

  @Test
  void all_sadPath_unableToFetchAllTheRecords() {
    when(mockBirdsRepository.getBirds()).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.all())
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ERROR_MESSAGE);
    verify(mockBirdsRepository).getBirds();
  }

  @Test
  void get_happyPath_ableToFetchRecord() {
    final Bird expectedBird = expectedBirds.get(0);
    when(mockBirdsRepository.getBird(ID)).thenReturn(expectedBird);
    final Bird actualBird = classUnderTest.get(ID);
    verifyResult(expectedBird, actualBird);
    verify(mockBirdsRepository).getBird(ID);
  }

  @Test
  void get_sadPath_unableToFetchRecord() {
    when(mockBirdsRepository.getBird(ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.get(ID))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ERROR_MESSAGE);
    verify(mockBirdsRepository).getBird(ID);
  }

  @Test
  void create_happyPath_ableToCreateRecord() {
    final Bird expectedBird = expectedBirds.get(0);
    when(mockBirdsRepository.save(expectedBird)).thenReturn(expectedBird);
    final Bird actualBird = classUnderTest.create(expectedBird);
    verifyResult(expectedBird, actualBird);
    verify(mockBirdsRepository).save(expectedBird);
  }

  @Test
  void create_sadPath_unableToCreateRecord() {
    final Bird expectedBird = expectedBirds.get(0);
    when(mockBirdsRepository.save(expectedBird)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.create(expectedBird))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ERROR_MESSAGE);
    verify(mockBirdsRepository).save(expectedBird);
  }

  @Test
  void update_happyPath_ableToUpdateRecord() {
    final Bird expectedBird = expectedBirds.get(0);
    when(mockBirdsRepository.update(ID, expectedBird)).thenReturn(expectedBird);
    final Bird actualBird = classUnderTest.update(ID, expectedBird);
    verifyResult(expectedBird, actualBird);
    verify(mockBirdsRepository).update(ID, expectedBird);
  }

  @Test
  void update_sadPath_unableToUpdateRecord() {
    final Bird expectedBird = expectedBirds.get(0);
    when(mockBirdsRepository.update(ID, expectedBird)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.update(ID, expectedBird))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ERROR_MESSAGE);
    verify(mockBirdsRepository).update(ID, expectedBird);
  }

  @Test
  void delete_happyPath_ableToDeleteRecord() {
    classUnderTest.delete(ID);
    verify(mockBirdsRepository).delete(ID);
  }

  @Test
  void delete_sadPath_unableToDeleteRecord() {
    final String errorMessage = "Unable to delete the record";
    doThrow(new RuntimeException(errorMessage)).when(mockBirdsRepository)
                                               .delete(ID);
    assertThatThrownBy(() -> classUnderTest.delete(ID))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(errorMessage);
    verify(mockBirdsRepository).delete(ID);
  }

  private void verifyResult(final Bird expectedBird, final Bird actualBird) {
    assertThat(actualBird.getId()).isEqualTo(expectedBird.getId());
    assertThat(actualBird.getName()).isEqualTo(expectedBird.getName());
    assertThat(actualBird.getDescription()).isEqualTo(expectedBird.getDescription());
    assertThat(actualBird.getGroup()).isEqualTo(expectedBird.getGroup());
  }
}