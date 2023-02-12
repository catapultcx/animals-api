package cx.catapult.animals.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BaseAnimal implements Serializable {

    private String id;
    private String name;
    private String description;
    private String colour;
    private String type;
    private Group group;

    public BaseAnimal(String name, String description, String colour, String type) {
        this(null, name, description, colour, type, Type.valueOf(type.toUpperCase()).getGroup());
    }

    public BaseAnimal(String id, String name, String description, String colour, String type, Group group) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.colour = colour;
        this.type = type;
        this.group = group;
    }
}
