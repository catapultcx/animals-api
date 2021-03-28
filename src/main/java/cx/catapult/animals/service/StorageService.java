package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import cx.catapult.animals.domain.Group;
import java.util.Collection;

public interface StorageService<T extends Animal> {

    public Collection<T> all();

    T create(T animal);

    public T get(String id);

    void delete(final String id);

    T update(final String id, final T newAnimal);

    void setGroup(final Group group);
}
