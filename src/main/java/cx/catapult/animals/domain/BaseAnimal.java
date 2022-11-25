package cx.catapult.animals.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Cat.class, name = "Cat")
})
public class BaseAnimal implements Animal, Serializable {

    private String id;
    private String name;
    private String description;
    private Classification classification;

    private String colour;

    public BaseAnimal(String name, String description, Classification classification, String colour) {
        this(null, name, description, classification, colour);
    }

    public BaseAnimal(String id, String name, String description, Classification classification, String colour) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.classification = classification;
        this.colour = colour;
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
    public Classification getClassification() {
        return this.classification;
    }

    @Override
    public String getColour() {
        return colour;
    }

    @Override
    public void setColour(String colour) {
        this.colour = colour;
    }

}
