package cx.catapult.animals.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.UUID;

public class Animal implements Serializable {

    private String id;
    private final String name;
    private final String description;
    private final AnimalType type;

    public static Animal aCat(String name, String description) {
        return new Animal(name, description, AnimalType.MAMMALS);
    }

    public static Animal anEagle(String name, String description) {
        return new Animal(name, description, AnimalType.BIRD);
    }

    @JsonCreator
    public Animal(String name, String description, AnimalType type) {
        this(null, name, description, type);
    }

    private Animal(String id, String name, String description, AnimalType type) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public AnimalType getType() {
        return this.type;
    }

    public void setId(String id) {
        this.id = id;
    }
}
