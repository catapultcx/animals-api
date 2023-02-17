package cx.catapult.animals.entity;

import cx.catapult.animals.domain.Group;
import cx.catapult.animals.validation.ColorCheck;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "animal")
public class AnimalEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @NotNull
    @NotBlank(message = "Name cannot be empty.")
    @Column(name = "name")
    private String name;

    @ColorCheck(message = "Color value is invalid.")
    @Column(name = "color")
    private String color;
    @NotNull
    @NotBlank(message = "Description cannot be empty.")
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name = "col_group")
    private Group group;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
