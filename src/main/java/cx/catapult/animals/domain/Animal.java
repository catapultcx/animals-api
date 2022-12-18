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

    void setType(String type);

    String getColour();

    void setColour(String colour);

}
