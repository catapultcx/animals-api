package cx.catapult.animals.domain;

import static cx.catapult.animals.domain.Group.MAMMALS;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class Cat extends BaseAnimal {

  public Cat() {
    this(EMPTY, EMPTY);
  }

  public Cat(final String name, final String description) {
    super(name, description, MAMMALS);
  }
}
