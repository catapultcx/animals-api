package cx.catapult.animals.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

public class Animal implements Serializable {

    private String id;
    private final String name;
    private final String description;
    private final String colour;
    private final AnimalType type;

    public static Animal aCat(String name, String description, String colour) {
        return new Animal(name, description, colour, AnimalType.MAMMALS);
    }

    public static Animal anEagle(String name, String description, String colour) {
        return new Animal(name, description, colour, AnimalType.BIRD);
    }

    @JsonCreator
    public Animal(String name, String description, String colour, AnimalType type) {
        this(null, name, description, colour, type);
    }

    private Animal(String id, String name, String description, String colour, AnimalType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.colour = colour;
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getColour() {
        return colour;
    }

    public AnimalType getType() {
        return this.type;
    }

    public void setId(String id) {
        this.id = id;
    }
}
