package cx.catapult.animals.domain;

import java.io.Serializable;

public class BaseAnimal implements Animal, Serializable {

    private String id;
    private String name;
    private String description;
    private final AnimalType type;

    public BaseAnimal(AnimalType type, String name, String description) {
        this(null, type, name, description);
    }

    public BaseAnimal(String id, AnimalType type, String name, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getType() {
        return this.type.name();
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
    public Group getGroup() {
        return this.type.getGroup();
    }
}
