package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;
import java.util.List;

public interface IService {
    Collection<Animal> all();

    Animal create(Animal animal);

    Animal get(String id);

    Animal update(Animal animal);

    void delete(String id);

    List<Animal> filter(String name, String description, String colour, String type);
}