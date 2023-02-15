package cx.catapult.animals.domain;


import cx.catapult.animals.validation.ColorCheck;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class Animal implements Serializable {

    private String id;
    @NotNull
    @NotBlank(message = "Name cannot be empty.")
    private String name;

    @ColorCheck(message = "Color value is invalid.")
    private String color;
    @NotNull
    @NotBlank(message = "Description cannot be empty.")
    private String description;
    private String type;
    private Group group;

    public Animal(String name, String description, String color) {
        this(null, null, name, description, color, null);
    }

    public Animal() {
        this(null, null, "", "", null, null);
    }

    public Animal(String id, String type, String name, String description, String color, Group group) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.color = color;
        this.group = group;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", name:'" + name + '\'' +
                ", description:'" + description + '\'' +
                ", type:" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return Objects.equals(getId(), animal.getId())
                && Objects.equals(getName(), animal.getName())
                && Objects.equals(getDescription(), animal.getDescription())
                && Objects.equals(getType(), animal.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getType());
    }

}
