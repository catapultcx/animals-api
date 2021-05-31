package cx.catapult.animals.domain;

public class Insect extends BaseAnimal {
    public Insect() {
        this("", "");
    }

    public Insect(String name, String description) {
        super(name, description, Group.INVERTEBRATE);
    }
}
