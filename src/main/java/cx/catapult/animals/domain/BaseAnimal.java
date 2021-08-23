package cx.catapult.animals.domain;

import java.io.Serializable;


public class BaseAnimal implements Animal, Serializable {


  private String id;
  private String name;
  private String description;
  private final Group group;

  public BaseAnimal(final String name, final String description, final Group group) {
    this(null, name, description, group);
  }

  public BaseAnimal(final String id, final String name, final String description, final Group group) {
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
  public void setId(final String id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public Group getGroup() {
    return this.group;
  }
}
