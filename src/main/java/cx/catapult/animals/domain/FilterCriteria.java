package cx.catapult.animals.domain;

public class FilterCriteria {
    private Group group;
    private String name;
    private Colour colour;
    private String description;

    public FilterCriteria(Group group, String name, Colour colour, String description) {
        this.group = group;
        this.name = name;
        this.colour = colour;
        this.description = description;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
