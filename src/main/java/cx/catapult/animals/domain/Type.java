package cx.catapult.animals.domain;

public enum Type {
    CAT("Cat", Group.MAMMALS),
    MOUSE("Mouse", Group.MAMMALS),
    SNAKE("Snake", Group.REPTILES),
    CROCODILE("Crocodile", Group.REPTILES),
    FISH("Fish", Group.FISH),
    TIGER("Tiger", Group.MAMMALS),
    BIRD("Bird", Group.BIRD);

    String name;
    Group group;

    Type(String name, Group group) {
        this.name = name;
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
}
