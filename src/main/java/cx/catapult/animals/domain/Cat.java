package cx.catapult.animals.domain;

public class Cat extends BaseAnimal {
    public Cat() {
        this("", "");
    }

    public Cat(String name, String description) {
        super(name, description, Group.MAMMALS);
    }

    public Cat(String name, String description, Colour[] colours) {
        super(name, description, Group.MAMMALS, colours);
    }

}
