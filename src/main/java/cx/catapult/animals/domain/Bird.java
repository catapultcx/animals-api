package cx.catapult.animals.domain;

import static cx.catapult.animals.domain.Group.BIRD;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class Bird extends BaseAnimal {

  public Bird() {
    this(EMPTY, EMPTY);
  }

  public Bird(final String name, final String description) {
    super(name, description, BIRD);
  }
}
