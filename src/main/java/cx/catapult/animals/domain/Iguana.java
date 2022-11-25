package cx.catapult.animals.domain;

public class Iguana extends BaseAnimal {
    public Iguana() {
        this("", "", "");
    }

    public Iguana(String name, String description, String colour) {
        super(name, description, Classification.REPTILES, colour);
    }
}
