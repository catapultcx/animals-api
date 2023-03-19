package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private final HashMap<String, T> items = new LinkedHashMap<>();

    @Override
    public Collection<T> all() {
        return items.values();
    }

    @Override
    public T create(T animal) {
        animal.setId(UUID.randomUUID().toString());
        return saveAndReturn(animal);
    }

    @Override
    public T get(String id) {
        return this.items.get(id);
    }

    @Override
    public T delete(String id) {
        return this.items.remove(id);
    }

    @Override
    public T update(String id, T animal) {
        animal.setId(id);
        return saveAndReturn(animal);
    }

    private T saveAndReturn(T animal) {
        this.items.put(animal.getId(), animal);
        return get(animal.getId());
    }
}
