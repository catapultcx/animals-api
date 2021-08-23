package cx.catapult.animals.service;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CatsRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(MockitoExtension.class)
class CatsServiceTest {

  private static final String ERROR_MESSAGE = "Unable to fetch records";
  private static final String ID = "ff8080817b6fd27e017b6fd282bb0000";

  @Mock
  private CatsRepository mockCatsRepository;
  @InjectMocks
  private CatsService classUnderTest;
  private List<Cat> expectedCats;

  @BeforeEach
  void setUp() {
    expectedCats = new PodamFactoryImpl().manufacturePojo(List.class, Cat.class);
  }

  @Test
  void initialize_happyPath_manageToInitialize() {
    classUnderTest.initialize();
    verify(mockCatsRepository).saveAll(anyList());
  }

  @Test
  void initialize_sadPath_notAbleToInitialize() {
    doThrow(RuntimeException.class).when(mockCatsRepository)
                                   .saveAll(anyList());
    assertThatThrownBy(() -> classUnderTest.initialize())
        .isInstanceOf(RuntimeException.class);
    verify(mockCatsRepository).saveAll(anyList());
  }

  @Test
  void all_happyPath_fetchedAllTheRecords() {
    when(mockCatsRepository.getCats()).thenReturn(expectedCats);
    final List<Cat> actualCats = (List<Cat>) classUnderTest.all();
    range(0, expectedCats.size())
        .forEach(counter -> verifyResult(expectedCats.get(counter), actualCats.get(counter)));
    verify(mockCatsRepository).getCats();
  }

  @Test
  void all_sadPath_unableToFetchAllTheRecords() {
    when(mockCatsRepository.getCats()).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.all())
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ERROR_MESSAGE);
    verify(mockCatsRepository).getCats();
  }

  @Test
  void get_happyPath_ableToFetchRecord() {
    final Cat expectedCat = expectedCats.get(0);
    when(mockCatsRepository.getCat(ID)).thenReturn(expectedCat);
    final Cat actualCat = classUnderTest.get(ID);
    verifyResult(expectedCat, actualCat);
    verify(mockCatsRepository).getCat(ID);
  }

  @Test
  void get_sadPath_unableToFetchRecord() {
    when(mockCatsRepository.getCat(ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.get(ID))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ERROR_MESSAGE);
    verify(mockCatsRepository).getCat(ID);
  }

  @Test
  void create_happyPath_ableToCreateRecord() {
    final Cat expectedCat = expectedCats.get(0);
    when(mockCatsRepository.save(expectedCat)).thenReturn(expectedCat);
    final Cat actualCat = classUnderTest.create(expectedCat);
    verifyResult(expectedCat, actualCat);
    verify(mockCatsRepository).save(expectedCat);
  }

  @Test
  void create_sadPath_unableToCreateRecord() {
    final Cat expectedCat = expectedCats.get(0);
    when(mockCatsRepository.save(expectedCat)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.create(expectedCat))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ERROR_MESSAGE);
    verify(mockCatsRepository).save(expectedCat);
  }

  @Test
  void update_happyPath_ableToUpdateRecord() {
    final Cat expectedCat = expectedCats.get(0);
    when(mockCatsRepository.update(ID, expectedCat)).thenReturn(expectedCat);
    final Cat actualCat = classUnderTest.update(ID, expectedCat);
    verifyResult(expectedCat, actualCat);
    verify(mockCatsRepository).update(ID, expectedCat);
  }

  @Test
  void update_sadPath_unableToUpdateRecord() {
    final Cat expectedCat = expectedCats.get(0);
    when(mockCatsRepository.update(ID, expectedCat)).thenThrow(new RuntimeException(ERROR_MESSAGE));
    assertThatThrownBy(() -> classUnderTest.update(ID, expectedCat))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ERROR_MESSAGE);
    verify(mockCatsRepository).update(ID, expectedCat);
  }

  @Test
  void delete_happyPath_ableToDeleteRecord() {
    classUnderTest.delete(ID);
    verify(mockCatsRepository).delete(ID);
  }

  @Test
  void delete_sadPath_unableToDeleteRecord() {
    final String errorMessage = "Unable to delete the record";
    doThrow(new RuntimeException(errorMessage)).when(mockCatsRepository)
                                               .delete(ID);
    assertThatThrownBy(() -> classUnderTest.delete(ID))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(errorMessage);
    verify(mockCatsRepository).delete(ID);
  }

  private void verifyResult(final Cat expectedCat, final Cat actualCat) {
    assertThat(actualCat.getId()).isEqualTo(expectedCat.getId());
    assertThat(actualCat.getName()).isEqualTo(expectedCat.getName());
    assertThat(actualCat.getDescription()).isEqualTo(expectedCat.getDescription());
    assertThat(actualCat.getGroup()).isEqualTo(expectedCat.getGroup());
  }
}