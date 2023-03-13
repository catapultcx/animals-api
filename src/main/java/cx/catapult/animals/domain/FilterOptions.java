package cx.catapult.animals.domain;

public class FilterOptions {
    private final String type;
    private final String name;
    private final String colour;
    private final String description;

    public FilterOptions(String type, String name, String colour, String description) {
        this.type = type;
        this.name = name;
        this.colour = colour;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    public String getDescription() {
        return description;
    }
}
