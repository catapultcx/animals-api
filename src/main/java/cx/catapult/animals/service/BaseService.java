package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private final Map<String, T> items = new ConcurrentHashMap<>();

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
        return items.get(id);
    }
}
