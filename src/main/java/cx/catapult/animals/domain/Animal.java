package cx.catapult.animals.domain;

public class Animal extends BaseAnimal {

    public Animal() {
        this("", "", "", "");
    }

    public Animal(String name, String description, String colour, String type) {
        super(name, description, colour, type);
    }
}
