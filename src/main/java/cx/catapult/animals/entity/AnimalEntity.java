package cx.catapult.animals.entity;

import cx.catapult.animals.domain.Group;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@DiscriminatorColumn(name="animal_type", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AnimalEntity {

    @Id
    private String id;

    private String name;

    private String description;

    @Column(name = "group_tag")
    private Group group;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }
}
