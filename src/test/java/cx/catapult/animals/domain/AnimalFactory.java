package cx.catapult.animals.domain;

public class AnimalFactory {

    public static final String ANIMAL_JSON_PAYLOAD = "{ \"name\": \"Tom\", \"description\": \"Bob cat\", \"colour\": \"White\", \"type\": \"MAMMALS\" }";

    public static Animal aCat() {
        return Animal.aCat("Tom", "Bob cat", "Orange");
    }
}
