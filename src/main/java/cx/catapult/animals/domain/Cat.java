package cx.catapult.animals.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class Cat extends BaseAnimal {

    private static final long serialVersionUID = 1L;

    public Cat() {
	this("", "");
    }

    public Cat(String name, String description) {
	super(name, description, Group.MAMMALS);
    }
}
