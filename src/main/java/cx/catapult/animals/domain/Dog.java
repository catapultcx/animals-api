package cx.catapult.animals.domain;

public class Dog extends BaseAnimal {
    public Dog() {
        this("", "");
    }

    public Dog(String name, String description) {
        super(name, description, Group.MAMMALS);
    }
}
