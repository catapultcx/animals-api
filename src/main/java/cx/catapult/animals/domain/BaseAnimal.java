package cx.catapult.animals.domain;

import java.io.Serializable;

public class BaseAnimal implements IAnimal, Serializable {

    private String id;
    private String name;
    private String description;
    private String colour;
    private Group group;

    public BaseAnimal(String name, String description, String colour, Group group) {
        this(null, name, description, colour, group);
    }

    public BaseAnimal(String id, String name, String description, String colour, Group group) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.colour = colour;
        this.group = group;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String getColour() {
        return this.colour;
    }

    @Override
    public Group getGroup() {
        return this.group;
    }
}
