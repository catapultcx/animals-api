package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;

public interface AnimalService {

    Animal createAnimalForOwner(String ownerId, Animal animal);

    Collection<Animal> getAllAnimalsForOwner(String ownerId);

    Animal getAnimalForOwner(String ownerId, String animalId);
    void removeAnimalForOwner(String ownerId, String animalId);
    Animal updateAnimalForOwner(String ownerId, Animal animal);

}
