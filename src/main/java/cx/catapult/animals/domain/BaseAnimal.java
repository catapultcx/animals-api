package cx.catapult.animals.domain;

import java.io.Serializable;

public class BaseAnimal implements AnimalIf, Serializable {

    private String id;
    private String name;
    private String description;
    private String colour;
    private AnimalType type;

    public BaseAnimal(String name, String description, String colour, String type) {
        this(null, name, description, colour, type);
    }

    public BaseAnimal(String id, String name, String description, String colour, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.colour = colour;
        this.type = AnimalType.get(type);
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
    public String getColour() {
        return this.colour;
    }

    @Override
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public AnimalType getType() {
        return this.type;
    }

    @Override
    public void setType(AnimalType type) {
        this.type = type;
    }
}
