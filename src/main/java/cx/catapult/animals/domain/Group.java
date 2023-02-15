package cx.catapult.animals.domain;

import java.util.Arrays;

public enum Group {

    AMPHIBIAN("amphibian"),
    BIRD("bird"),
    FISH("fish"),
    INVERTEBRATE("invertebrate"),
    MAMMALS("mammals"),
    REPTILES("reptiles");

    private String val;
    Group(String val) {
        this.val = val;
    }

    public static Group get(String val) {
        return Arrays.stream(Group.values())
                .filter(item -> item.val.equals(val))
                .findFirst().orElse(null);
    }

}
