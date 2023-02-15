package cx.catapult.animals.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Type {

    AMPHIBIAN("amphibian"),
    BIRD("bird"),
    FISH("fish"),
    INVERTEBRATE("invertebrate"),
    MAMMALS("mammals"),
    REPTILES("reptiles");

    private String val;
    Type(String val) {
        this.val = val;
    }

    @JsonCreator
    public static Type get(String val) {
        return Arrays.stream(Type.values())
                .filter(item -> item.val.equalsIgnoreCase(val))
                .findFirst().orElse(null);
    }

}
