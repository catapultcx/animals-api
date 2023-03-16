package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.web.AnimalFilter;

import java.util.Collection;

public interface Service<T extends Animal> {

    Collection<T> all();

    T create(T animal);

    T get(String id);

    void delete(String id);

    T update(String id, T animal);

    <T>  Collection<T> search(AnimalFilter filter);
}
