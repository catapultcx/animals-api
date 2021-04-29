package cx.catapult.animals.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "Animal")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator")
public class BaseAnimal implements Animal, Serializable {
	@Id
    private String id;
	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	@Column(name = "group_name")
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BaseAnimal that = (BaseAnimal) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(name, that.name) &&
				Objects.equals(description, that.description) &&
				group == that.group;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, group);
	}
}
