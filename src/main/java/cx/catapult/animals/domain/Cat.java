package cx.catapult.animals.domain;

public class Cat extends BaseAnimal {
    public Cat() {
        this("", "");
    }

    public Cat(String name, String description) {
        super(AnimalType.CAT, name, description);
    }
}
