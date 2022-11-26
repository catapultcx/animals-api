package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;
import java.util.HashSet;

public final class AnimalFilter {

    private AnimalFilter() {
        //private constructor
    }

    public static Collection<Animal> filterAnimals(Collection<Animal> animalsToFilter,
                                                   String type,
                                                   String name,
                                                   String colour,
                                                   String description) {
        if (allFilterAreBlankOrNull(type, name, colour, description)) {
            return animalsToFilter;
        }
        var toReturn = new HashSet<>(animalsToFilter);

        if (type != null && !type.isBlank()) {
            toReturn.removeIf(a -> !a.type().equalsIgnoreCase(type.trim()));
        }
        if (name != null && !name.isBlank()) {
            toReturn.removeIf(a -> !a.name().equalsIgnoreCase(name.trim()));
        }
        if (colour != null && !colour.isBlank()) {
            toReturn.removeIf(a -> !a.colour().equalsIgnoreCase(colour.trim()));
        }
        if (description != null && !description.isBlank()) {
            toReturn.removeIf(a -> !a.description().equalsIgnoreCase(description.trim()));
        }

        return toReturn;
    }

    private static boolean allFilterAreBlankOrNull(String type, String name, String colour, String description) {
        return  notNullOrBlank(type)
            && notNullOrBlank(name)
            && notNullOrBlank(colour)
            && notNullOrBlank(description);
    }

    private static boolean notNullOrBlank(String type) {
        return type == null || type.isBlank();
    }


}
