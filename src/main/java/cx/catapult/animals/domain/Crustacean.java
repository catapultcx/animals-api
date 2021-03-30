package cx.catapult.animals.domain;

import javax.persistence.Entity;

@Entity
public class Crustacean extends BaseAnimal {
    public Crustacean(String name, String description) {
        super(name, description, Group.INVERTEBRATE);
    }

    public Crustacean() {}
}
