package cx.catapult.animals.domain;

public interface Animal {

    String getId();

    void setId(String id);

    String getName();

    void setName(String color);

	String getColor();

	void setColor(String color);

    String getDescription();

    void setDescription(String description);

    Group getGroup();

	String getType();

}
