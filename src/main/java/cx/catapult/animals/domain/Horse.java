package cx.catapult.animals.domain;

import lombok.Builder;

public class Horse extends BaseAnimal {
    public Horse() {
        this("", "");
    }

    public Horse(String name, String description) {
        super(name, description, Group.MAMMALS);
    }

    @Builder
    public Horse(String id, String name, String description, Group group) {
        super(id, name, description, group);
    }
}
