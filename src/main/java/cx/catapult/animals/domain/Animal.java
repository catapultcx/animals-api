package cx.catapult.animals.domain;

public interface Animal {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    Group getGroup();

    String getType();

    String getColor();

    void setType(String type);

    void setColor(String color);

}
