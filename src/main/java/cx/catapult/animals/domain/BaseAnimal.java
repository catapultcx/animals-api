package cx.catapult.animals.domain;

import java.io.Serializable;

public class BaseAnimal implements Animal, Serializable {

    private String id;
    private String name;
    private String description;
    private Group group;
    private String colour;
    private String type;

    // Default constructor for Jackson
    private BaseAnimal() {
    }

    public BaseAnimal(String name, String description, Group group, String type) {
        this(null, name, description, group, type);
    }

    public BaseAnimal(String id, String name, String description, Group group, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.group = group;
        this.type = type;
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
    public Group getGroup() {
        return this.group;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) { this.type = type; }

    @Override
    public String getColour() {
        return colour;
    }

    @Override
    public void setColour(String colour) {
        this.colour = colour;
    }
}
