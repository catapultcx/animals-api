package cx.catapult.animals.domain;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

public class BaseAnimal implements Animal, Serializable {

    private String id;
    private String name;
    private String description;
    private Group group;

    public BaseAnimal(String name, String description, Group group) {
        this(null, name, description, group);
    }

    public BaseAnimal(String id, String name, String description, Group group) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
