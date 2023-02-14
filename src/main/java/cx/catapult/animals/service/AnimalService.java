package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Group;

import java.util.*;

public class AnimalService {

    private final String animalType;
    private final HashMap<String, Animal> items = new HashMap<>();
    private final Group group;

    public AnimalService(String animalType, Group group) {
        this.animalType = animalType;
        this.group = group;
    }


    public Collection<Animal> all() {
        return items.values();
    }

    public Animal create(Animal animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        animal.setType(this.animalType);
        animal.setGroup(this.group);
        items.put(id, animal);
        return animal;
    }

    public Animal get(String id) {
        return items.get(id);
    }
}
