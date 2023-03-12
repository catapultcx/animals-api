package cx.catapult.animals.domain;

public class Cat extends BaseAnimal {
    public Cat() {
        this("", "");
    }

    public Cat(String name, String description) {
        super(name, description, Group.MAMMALS);
    }

    public Cat(String id, String name, String description) {
        super(id, name, description, Group.MAMMALS);
    }
}
