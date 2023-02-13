package cx.catapult.animals.domain;

import lombok.Builder;

import java.io.Serializable;
public class AnimalImpl implements Animal, Serializable {

	private String id;
	private String name;
	private String description;
	private String color;
	private String type;
	private Group group;


	public AnimalImpl() {

	}
	public AnimalImpl(String name, String description, Group group, String color, String type) {
		this(null, name, description, group, color, type);
	}

	public AnimalImpl(String id, String name, String description, Group group, String color, String type) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.group = group;
		this.color = color;
		this.type = type;
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
	public String getColor() {
		return color;
	}

	@Override
	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
