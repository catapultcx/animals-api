package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;

public interface Service<T extends Animal> {

    public Collection<T> all();

    T create(T animal);

    T update(String id, T animal);

    public T get(String id);

}
