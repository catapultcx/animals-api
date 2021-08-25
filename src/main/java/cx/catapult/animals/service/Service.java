package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;
import java.util.Optional;

public interface Service<T extends Animal> {

    public Collection<T> all();

    T create(T animal);

    public T get(String id);

    Optional<T> delete(String id);

}
