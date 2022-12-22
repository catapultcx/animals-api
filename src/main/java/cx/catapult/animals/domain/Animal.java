package cx.catapult.animals.domain;

public interface Animal {

    String getId();

    void setId(String id);

    String getSpecies();

    void setSpecies(String species);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    Group getGroup();

    void setGroup(Group group);

}
