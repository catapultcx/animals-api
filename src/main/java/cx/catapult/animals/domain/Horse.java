package cx.catapult.animals.domain;

public class Horse extends BaseAnimal {
    public Horse() {
        this("", "");
    }

    public Horse(String name, String description) {
        super(name, description, Group.MAMMALS);
    }
}
