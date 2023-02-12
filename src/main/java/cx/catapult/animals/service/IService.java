package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;

public interface IService {
    Collection<Animal> all();

    Animal create(Animal animal);

    Animal get(String id);

    Animal update(Animal animal);

    void delete(String id);
}