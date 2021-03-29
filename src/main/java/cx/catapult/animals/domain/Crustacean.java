package cx.catapult.animals.domain;

public class Crustacean extends BaseAnimal{
    public Crustacean(String name, String description) {
        super(name, description, Group.INVERTEBRATE);
    }
}
