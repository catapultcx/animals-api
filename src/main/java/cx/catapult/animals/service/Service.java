package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import cx.catapult.animals.exceptions.AnimalException;
import java.util.Collection;

public interface Service<T extends Animal> {

    Collection<T> all();

    T create(T animal);

    T get(String id);

    void remove(String key) throws AnimalException;

    T update(String key, T animal) throws AnimalException;

}
