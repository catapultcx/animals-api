package cx.catapult.animals.domain;

public class UpdateAnimal {
    private final String name;
    private final String type;
    private final String color;
    private final String description;

    public UpdateAnimal(String name, String type, String color, String description) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

}
