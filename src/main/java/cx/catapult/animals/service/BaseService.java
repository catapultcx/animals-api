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
    public void update(T animal) {
        items.put(animal.getId(), animal);
    }

    @Override
    public void delete(String id) {
        items.remove(id);
    }
    @Override
    public T get(String id) {
        return items.get(id);
    }
}
