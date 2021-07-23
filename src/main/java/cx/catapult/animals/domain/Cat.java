package cx.catapult.animals.domain;

import lombok.Builder;

public class Cat extends BaseAnimal {
    public Cat() {
        this("", "");
    }

    @Builder
    public Cat(String id, String name, String description, Group group) {
        super(id, name, description, group);
    }

    public Cat(String name, String description) {
        super(name, description, Group.MAMMALS);
    }
}
