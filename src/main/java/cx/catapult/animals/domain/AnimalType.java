package cx.catapult.animals.domain;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
public enum AnimalType {

    AMPHIBIAN("amphibian"),
    BIRD("bird"),
    FISH("fish"),
    INVERTEBRATE("invertebrate"),
    MAMMALS("mammals"),
    REPTILES("reptiles");

    private final String val;

    AnimalType(String val) {
        this.val = val;
    }

    @JsonCreator
    public static AnimalType get(String val) {
        return Arrays.stream(AnimalType.values())
                .filter(item -> item.val.equalsIgnoreCase(val))
                .findFirst().orElse(null);
    }
}
