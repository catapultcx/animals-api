package cx.catapult.animals.exception;

import static java.lang.String.format;

public class AnimalNotFoundException extends RuntimeException {

  public AnimalNotFoundException(final String uuid) {
    super(format("Could not find the animal with id %s", uuid));
  }
}
