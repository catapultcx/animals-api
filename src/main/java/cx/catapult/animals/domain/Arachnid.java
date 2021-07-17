package cx.catapult.animals.domain;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;

public class Arachnid extends BaseAnimal {
    public Arachnid() {
        this("", "");
    }

    public Arachnid(String name, String description) {
        super(name, description, Group.INVERTEBRATE);
    }
}
