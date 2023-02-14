package cx.catapult.animals.domain;

import cx.catapult.animals.exceptions.UnsupportedAnimalTypeException;

public interface Animal {

    static Animal from(String type, String name, String description) throws UnsupportedAnimalTypeException {
        AnimalType animalType;
        try {
            animalType = AnimalType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedAnimalTypeException(type);
        }

        return new BaseAnimal(animalType, name, description);

    }
    enum AnimalType {
        CAT(Group.MAMMALS),
        DOG(Group.MAMMALS),
        OWL(Group.BIRD),
        PARROT(Group.BIRD),
        SPIDER(Group.INVERTEBRATE),
        TUNA(Group.FISH),
        SALMON(Group.FISH),
        FROG(Group.AMPHIBIAN),
        IGUANA(Group.REPTILES);



        private final Group group;
        AnimalType(Group group) {
            this.group = group;
        }

        public Group getGroup() {
            return group;
        }

    }


    String getType();

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    Group getGroup();


}
