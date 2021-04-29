package cx.catapult.animals.exception;

import lombok.Getter;
import lombok.NonNull;

import static java.lang.String.format;

@Getter
public class DataNotFoundException extends RuntimeException {

  private final Class<?> clazz;
  private final String val;

  public DataNotFoundException(@NonNull Class<?> clazz, String val) {
    super(format("%s by '%s' was not found", clazz.getSimpleName(), val));
    this.clazz = clazz;
    this.val = val;
  }
}
