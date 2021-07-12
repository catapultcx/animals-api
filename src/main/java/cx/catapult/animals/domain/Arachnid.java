package cx.catapult.animals.domain;

public class Arachnid extends BaseAnimal {

    public Arachnid() {
        this("", "");
    }

    public Arachnid(String name, String description) {
        super(name, description, Group.AMPHIBIAN);
    }
}
