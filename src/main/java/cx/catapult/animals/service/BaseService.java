package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private HashMap<String, T> items = new HashMap<>();

    @Override
    public Collection<T> all(String name, String description) {
        Collection<T> animals = items.values();
        if (name != null) {
            animals = animals.stream().filter(cat -> cat.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (description != null) {
            animals = animals.stream().filter(cat -> cat.getDescription().toLowerCase().contains(description.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return animals;
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
    public T delete(String id) {
        return items.remove(id);
    }

    @Override
    public T update(String id, T animal) {
        items.put(id, animal);
        return animal;
    }
}
