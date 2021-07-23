package cx.catapult.animals.domain;

import static java.util.Arrays.stream;

public enum Group {

    AMPHIBIAN,
    BIRD,
    FISH,
    INVERTEBRATE,
    MAMMALS,
    REPTILES;

    public static Group toGroup(String value) throws IllegalArgumentException {
        return stream(values())
                .filter(group -> group.name().equalsIgnoreCase(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(value));
    }

}
