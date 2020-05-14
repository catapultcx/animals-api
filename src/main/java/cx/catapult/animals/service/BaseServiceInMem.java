package cx.catapult.animals.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import cx.catapult.animals.domain.Animal;

public abstract class BaseServiceInMem<T extends Animal> implements Service<T> {

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
        return items.get(id);

    }

    @Override
    public T update(T animal) {
        items.put(animal.getId(), animal);
        return animal;

    }

    @Override
    public void delete(String id) {
        items.remove(id);
    }
}
