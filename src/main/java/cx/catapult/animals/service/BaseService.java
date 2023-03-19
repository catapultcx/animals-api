package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;

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
    public T update(String id, T animal) {
        items.put(id, animal);
        return items.get(id);
    }

    @Override
    public T get(String id) {
        return items.get(id);
    }

    @Override
    public Collection<T> delete(String id) {
        items.remove(id);
        return items.values();
    }
}
