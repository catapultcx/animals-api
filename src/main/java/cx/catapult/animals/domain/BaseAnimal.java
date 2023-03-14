package cx.catapult.animals.domain;

import java.io.Serializable;

public class BaseAnimal implements Animal, Serializable {

    private String id;
    private String name;
    private String description;
    private String color;
    private Type type;

    public BaseAnimal(String name, String description, String color, Type type) {
        this.name = name;
        this.description = description;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
