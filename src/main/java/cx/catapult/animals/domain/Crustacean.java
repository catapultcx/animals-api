package cx.catapult.animals.domain;

public class Crustacean extends BaseAnimal {
    public Crustacean(final String name,
                      final String description) {
        super(name, description, Group.INVERTEBRATE);
    }

}
