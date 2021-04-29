package cx.catapult.animals.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * A Crustacean is a type of invertebrate, which includes animals such as crabs, lobsters, crayfish, shrimps
 * and prawns.
 */
@Entity
@DiscriminatorValue(value = "INVERTEBRATE")
public class Crustacean extends BaseAnimal {
    public Crustacean() {
        this("", "");
    }

    public Crustacean(String name, String description) {
        super(name, description, Group.INVERTEBRATE);
    }
}
