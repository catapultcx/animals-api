package cx.catapult.animals.domain;

public class Cat extends BaseAnimal {
    public Cat() {
        this("", "");
    }

    public Cat(String name, String description) {
        super(name, description, Group.MAMMALS);
    }

    public Cat(String ID, String name, String description) {
        super(ID, name, description, Group.MAMMALS);
    }
}
