package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.web.AnimalFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Predicate;

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
    public void delete(String id) {
        items.remove(id);
    }

    @Override
    public T update(String id, T animal) {
        animal.setId(id);
        items.put(id, animal);
        return animal;
    }

    protected HashMap<String, T> getAnimalStore() {
        return items;
    }
}
