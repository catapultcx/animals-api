package cx.catapult.animals.domain;

import java.io.Serializable;

public class BaseAnimal implements Animal, Serializable {

    private String id;
    private String name;
    private String type;
    private String color;
    private String description;
    private Group group;

    public BaseAnimal() { }

    public BaseAnimal(String name, String type, String color, String description, Group group) {
        this(null, name, type, color, description, group);
    }

    public BaseAnimal(String id, String name, String type, String color, String description, Group group) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.color = color;
        this.description = description;
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
    public Group getGroup() {
        return this.group;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }
}
