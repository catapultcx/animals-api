package cx.catapult.animals.domain;

import javax.persistence.Entity;

@Entity
public class Dog extends BaseAnimal {
    public Dog() {
        this("", "");
    }

    public Dog(String name, String description) {
        super(name, description, Groop.MAMMALS);
    }
}
