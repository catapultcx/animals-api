package cx.catapult.animals.domain;

import javax.persistence.Entity;

@Entity
public class Cat extends BaseAnimal {
    public Cat() {
        this("", "");
    }

    public Cat(String name, String description) {
        super(name, description, Groop.MAMMALS);
    }
}
