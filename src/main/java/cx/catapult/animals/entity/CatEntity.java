package cx.catapult.animals.entity;

import cx.catapult.animals.domain.Group;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CATS")
public class CatEntity {

    @Id
    @Column(name = "id", updatable = true, nullable = false)
    private UUID id = UUID.randomUUID();

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Group genus;

    public Group getGenus() {
        return genus;
    }

    public void setGenus(Group group) {
        this.genus = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
