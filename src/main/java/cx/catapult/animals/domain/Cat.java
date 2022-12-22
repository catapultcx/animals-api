package cx.catapult.animals.domain;

public class Cat extends BaseAnimal {
    private static final String SPECIES = "Cat";
    private static final Group GROUP = Group.MAMMALS;

    public Cat() {
        this("", "");
    }

    public Cat(String name, String description) {
        super(name, description, SPECIES, GROUP);
    }
}
