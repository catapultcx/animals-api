package cx.catapult.animals.domain;

public class Generic extends BaseAnimal {
    private static final String SPECIES = "Not specified";
    private static final Group GROUP = Group.UNKNOWN;

    public Generic() {
        this("", "");
    }

    public Generic(String name, String description) {
        this(name, description, SPECIES, GROUP);
    }

    public Generic(String name, String description, String species, Group group) {
        super(name, description, species, group);
    }
}
