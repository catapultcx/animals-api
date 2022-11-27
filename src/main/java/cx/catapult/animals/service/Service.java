package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;
import java.util.List;

public interface Service {

    Collection<Animal> all();

    Animal create(Animal animal);

    Animal get(String id);

    Animal delete(String id);

    Animal update(String id, Animal animalToUpdate);

    List<Animal> filter(String searchTerm);
}
