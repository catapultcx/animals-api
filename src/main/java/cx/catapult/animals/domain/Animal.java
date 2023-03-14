package cx.catapult.animals.domain;

public interface Animal {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    void setType(Type type);

    Type getType();

    void setColor(String color);

    String getColor();

}
