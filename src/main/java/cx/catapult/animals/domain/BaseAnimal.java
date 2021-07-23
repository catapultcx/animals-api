package cx.catapult.animals.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class BaseAnimal implements Animal, Serializable {

    public static final String CAT_STRING = "CAT";
    public static final String HORSE_STRING = "HORSE";

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
}
