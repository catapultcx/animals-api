package cx.catapult.animals.domain;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class BaseAnimal implements Animal, Serializable {

    private String id;
    @NotBlank(message = "Name cannot be null or empty")
    private String name;
    @NotBlank(message = "Description cannot be null or empty")
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
