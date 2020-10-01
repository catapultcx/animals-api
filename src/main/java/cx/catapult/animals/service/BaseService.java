package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private HashMap<String, T> items = new HashMap<>();

    @Override
    public Collection<T> all() {
        query("select a.id, a.name, a.description, t.name as type, t.group"
                + " from animal a left join animaltype t on a.type = t.id where a.type = ?");
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
}
