package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public abstract class BaseService<T extends BaseAnimal> implements Service<T> {

    private final HashMap<String, T> items = new HashMap<>();

    @Override
    public Collection<T> all() {
        return items.values();
    }

    @Override
    public T create(final T animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        items.put(id, animal);
        return animal;
    }

    @Override
    public T get(final String id) {
        return items.get(id);
    }

    @Override
    public boolean delete(final String id) {
        if (items.containsKey(id)) {
            items.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public T update(final T animal) {
        items.replace(animal.getId(), animal);
        return items.get(animal.getId());
    }
}
