package cx.catapult.animals.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseAnimal implements Animal, Serializable {

    @Id
    private String id;

    private String name;
    private String description;
    private Groop groop;

    public BaseAnimal(String name, String description, Groop groop) {
        this(null, name, description, groop);
    }

    public BaseAnimal(String id, String name, String description, Groop groop) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.groop = groop;
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
    public Groop getGroop() {
        return this.groop;
    }
}
