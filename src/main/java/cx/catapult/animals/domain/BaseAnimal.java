package cx.catapult.animals.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseAnimal implements Serializable {

    private String id;
    private String name;
    private String description;
    private String colour;
    private String type;
    private Group group;

    public BaseAnimal(String name, String description, String colour, String type) {
        this.name = name;
        this.description = description;
        this.colour = colour;
        this.type = type;
        this.group = Type.valueOf(type.toUpperCase()).getGroup();
    }
}
