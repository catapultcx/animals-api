package cx.catapult.animals.domain;

public class Turtle extends BaseAnimal {
    public Turtle() {
        this("", "");
    }

    public Turtle(String name, String description) {
        super(name, description, Group.REPTILES);
    }
}
