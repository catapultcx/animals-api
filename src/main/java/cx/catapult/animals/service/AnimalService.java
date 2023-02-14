package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;

public class AnimalService {

    private final Animal.AnimalType animalType;
    private final HashMap<String, Animal> items = new HashMap<>();

    protected AnimalService(Animal.AnimalType animalType) {
        this.animalType = animalType;
    }


    public Collection<Animal> all() {
        return items.values();
    }

    public Animal create(Animal animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        animal.setType(this.animalType);
        items.put(id, animal);
        return animal;
    }

    public Animal get(String id) {
        return items.get(id);
    }
}
