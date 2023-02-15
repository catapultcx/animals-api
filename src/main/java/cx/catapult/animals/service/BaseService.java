package cx.catapult.animals.service;

import cx.catapult.animals.domain.IAnimal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseService<T extends IAnimal> implements IService<T> {

    private Map<String, T> items = new ConcurrentHashMap<>();

    @Override
    public Collection<T> all() {
        return items.values();
    }

    @Override
    public T get(String id) {
        return items.get(id);
    }

    @Override
    public T create(T animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        items.put(id, animal);
        return animal;
    }

    @Override
    public T update(T animal) {
        T existingAnimal = items.get(animal.getId());
        existingAnimal.setName(animal.getName());
        existingAnimal.setDescription(animal.getDescription());
        existingAnimal.setColour(animal.getColour());
        existingAnimal.setType(animal.getType());
        return items.get(animal.getId());
    }

    @Override
    public void delete(String id) {
        items.remove(id);
    }
}
