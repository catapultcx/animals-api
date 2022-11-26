package cx.catapult.animals.domain;

import java.io.Serializable;
import java.util.UUID;

public class BaseAnimal implements Animal, Serializable {

    private final String id;
    private final String name;
    private final String description;
    private final Group group;

    public BaseAnimal(String name, String description, Group group) {
        this(UUID.randomUUID().toString(), name, description, group);
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
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Group getGroup() {
        return this.group;
    }
}
