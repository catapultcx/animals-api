package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;

public interface Service {

    Collection<Animal> all();

    Animal create(Animal animal);

    Animal get(String id);

    Animal delete(String id);

}
