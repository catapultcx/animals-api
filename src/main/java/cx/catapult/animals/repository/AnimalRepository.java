package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;

public interface AnimalRepository {

    Animal createAnimalForOwner(String ownerId, Animal animal);

    Collection<Animal> getAllAnimalsForOwner(String ownerId);

    Animal getAnimalForOwner(String ownerId, String animalId);

}
