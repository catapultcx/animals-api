package cx.catapult.animals.domain;

import java.util.Objects;
import java.util.Optional;

public class Cat extends BaseAnimal {
    public Cat() {
        this("", "");
    }

    public Cat(String name, String description) {
        super(name, description, Group.MAMMALS);
    }

    @Override
    public boolean equals(Object other){
        if (other == null)
            return false;
        if (!(other instanceof Cat))
            return false;
        Cat otherCat = (Cat) other;
        return this.getName().equals(otherCat.getName())
                && this.getDescription().equals(otherCat.getDescription())
                && Optional.ofNullable(this.getId()).equals(Optional.ofNullable(otherCat.getId()))
                && this.getGroup() == otherCat.getGroup();
    }
}
