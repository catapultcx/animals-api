package cx.catapult.animals.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseAnimal implements Animal, Serializable {

    @Id
    private String id;
    private String name;
    private String description;
    private AnimalGroup animalGroup;

    public BaseAnimal(String name, String description, AnimalGroup animalGroup) {
        this(null, name, description, animalGroup);
    }

    public BaseAnimal(String id, String name, String description, AnimalGroup animalGroup) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.animalGroup = animalGroup;
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
    public AnimalGroup getAnimalGroup() {
        return this.animalGroup;
    }
}
