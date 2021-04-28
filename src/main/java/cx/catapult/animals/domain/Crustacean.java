package cx.catapult.animals.domain;

/**
 * A Crustacean is a type of invertebrate, which includes animals such as crabs, lobsters, crayfish, shrimps
 * and prawns.
 */
public class Crustacean extends BaseAnimal {
    public Crustacean() {
        this("", "");
    }

    public Crustacean(String name, String description) {
        super(name, description, Group.INVERTEBRATE);
    }
}
