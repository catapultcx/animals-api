package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;

public interface Service<T extends Animal> {

    public Collection<T> all(String name, String description);

    T create(T animal);

    public T get(String id);

    public T delete(String id);

    public T update(String id, T animal);
}
