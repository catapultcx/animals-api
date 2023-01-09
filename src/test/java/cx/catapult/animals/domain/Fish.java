package cx.catapult.animals.domain;

public class Fish extends BaseAnimal {
    public Fish() {
        this("", "");
    }

    public Fish(String name, String description) {
        super(name, description, Group.FISH);
    }

    public Fish(String name, String description, Colour[] colours) {
        super(name, description, Group.FISH, colours);
    }

}
