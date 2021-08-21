package cx.catapult.animals.domain;

import javax.persistence.Entity;

@Entity
public class Bird extends BaseAnimal {
    public Bird() {
        this("", "");
    }

    public Bird(String name, String description) {
        super(name, description, AnimalGroup.BIRD);
    }
}
