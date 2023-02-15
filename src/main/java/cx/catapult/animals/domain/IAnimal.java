package cx.catapult.animals.domain;

public interface IAnimal {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getColour();

    void setColour(String colour);

    Group getGroup();

}
