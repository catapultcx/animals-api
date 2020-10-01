package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;

public interface Service<T extends Animal> {

    public Collection<T> query(String sql);

    public Collection<T> all();

    T create(T animal);

    public T get(String id);

    T update(String id, T animal);

    void delete(String id);
}
