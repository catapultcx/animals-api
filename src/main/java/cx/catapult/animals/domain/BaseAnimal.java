package cx.catapult.animals.domain;

import java.io.Serializable;

public class BaseAnimal implements Animal, Serializable {

    private String id;
    private String species;
    private String name;
    private String description;
    private Group group;

    public BaseAnimal(String species, String name, String description, Group group) {
        this(null, species, name, description, group);
    }

    public BaseAnimal(String id, String name, String description, String species, Group group) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.species = species;
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
    public String getSpecies() {
        return this.species;
    }

    @Override
    public void setSpecies(String species) {
        this.species = species;
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
    public void setGroup(Group group) { this.group = group; }

}
