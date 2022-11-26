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
        items.put(animal.getId(), animal);
        return animal;
    }

    @Override
    public T get(String id) {
        return items.get(id);
    }
}
