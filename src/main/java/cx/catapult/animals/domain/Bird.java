package cx.catapult.animals.domain;

public class Bird extends BaseAnimal {
    public Bird() {
        this("", "");
    }

    public Bird(String name, String description) {
        super(name, description, Group.BIRD);
    }

    public Bird(String ID, String name, String description) {
        super(ID, name, description, Group.BIRD);
    }
}
