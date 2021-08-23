package cx.catapult.animals.exception;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class AnimalNotFoundExceptionTest {

  private static final String ERROR_MESSAGE = "Could not find the animal with id %s";

  @ParameterizedTest
  @ValueSource(strings = {"ff8080817b6fd27e017b6fd282bb0000", EMPTY})
  @NullSource
  void getMessage_happyPath_returnedMessage(final String id) {
    assertThat(new AnimalNotFoundException(id).getMessage()).isEqualTo(format(ERROR_MESSAGE, id));
  }
}