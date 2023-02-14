package cx.catapult.animals.domain;


import java.io.Serializable;
import java.util.Objects;

public class Animal implements Serializable {

    private String id;
    private String name;
    private String description;
    private String type;
    private Group group;

    public Animal(String name, String description) {
        this(null, null, name, description, null);
    }

    public Animal() {
        this(null, null, "", "", null);
    }

    public Animal(String id, String type, String name, String description, Group group) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
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
