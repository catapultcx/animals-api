package cx.catapult.animals.domain;

public class Reptile extends BaseAnimal{

    public Reptile() {
        this("", "");
    }

    public Reptile(String name, String description) {
        super(name, description, Group.REPTILES);
    }

}
