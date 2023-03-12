package cx.catapult.animals.service;

import java.util.Collection;

import cx.catapult.animals.domain.Animal;

public interface Service<T extends Animal> {

    public Collection<T> all();

    T create(T animal);

    public T get(String id);

    public T update(T animal);

    public T remove(String id);
}
