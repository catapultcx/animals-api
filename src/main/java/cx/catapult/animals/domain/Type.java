package cx.catapult.animals.domain;

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

    public static Type get(String val) {
        return Arrays.stream(Type.values())
                .filter(item -> item.val.equals(val))
                .findFirst().orElse(null);
    }

}
