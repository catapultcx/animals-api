package cx.catapult.animals.domain;

import cx.catapult.animals.exceptions.UnsupportedAnimalTypeException;

import java.io.Serializable;
import java.util.Objects;

public class Animal implements Serializable {
    public static Animal from(String type, String name, String description) throws UnsupportedAnimalTypeException {
        AnimalType animalType;
        try {
            animalType = AnimalType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedAnimalTypeException(type);
        }

        return new Animal(animalType, name, description);

    }
    public enum AnimalType {
        CAT(Group.MAMMALS),
        DOG(Group.MAMMALS),
        OWL(Group.BIRD),
        PARROT(Group.BIRD),
        SPIDER(Group.INVERTEBRATE),
        TUNA(Group.FISH),
        SALMON(Group.FISH),
        FROG(Group.AMPHIBIAN),
        IGUANA(Group.REPTILES);



        private final Group group;
        AnimalType(Group group) {
            this.group = group;
        }

        public Group getGroup() {
            return group;
        }

    }


    private String id;
    private String name;
    private String description;
    private AnimalType type;

    public Animal(AnimalType type, String name, String description) {
        this(null, type, name, description);
    }
    public Animal() {
        this(null, null, "", "");
    }

    public Animal(String id, AnimalType type, String name, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public String getType() {
        return this.type.name();
    }

    public void setType(AnimalType type) {
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
        return this.type.getGroup();
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
