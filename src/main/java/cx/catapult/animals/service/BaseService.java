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
    public T get(String id) {
        return items.get(id);
    }

    @Override
    public T remove(String id) {
        if (items.containsKey(id)) {
            return items.remove(id);
        }
        return null;
    }

    @Override
    public T save(String id, String name, String desc) {
        T entity = items.get(id);
        entity.setName(name);
        entity.setDescription(desc);
        items.put(id, entity);
        return entity;
    }

}
