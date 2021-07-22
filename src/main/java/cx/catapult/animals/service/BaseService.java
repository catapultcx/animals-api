package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.exception.AnimalNotFoundException;

import java.util.*;

import static java.util.Objects.isNull;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private HashMap<String, T> items = new HashMap<>();

    @Override
    public Collection<T> all() {
        return items.values();
    }

    @Override
    public T create(T animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        items.put(id, animal);
        return animal;
    }

    @Override
    public T get(String id) {
        T item = items.get(id);
        if (isNull(item)) {
            throw new AnimalNotFoundException();
        }
        return item;
    }

    @Override
    public void delete(String id) {
        T item = items.remove(id);
        if (isNull(item)) {
            throw new AnimalNotFoundException();
        }
    }
}
