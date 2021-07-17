package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;

public abstract class BaseService<T extends Animal> implements Service<T> {

    protected HashMap<String, T> items = new HashMap<>();

    @Override
    public Collection<T> all() {
        return items.values();
    }

    // For simplicity of error handling this is implemented as saveOrUpdate ie. will create if not exists
    @Override
    public T update(final String id, final T animal){
        animal.setId(id);
        items.put(id, animal);

        return animal;
    }

    @Override
    public T create(T animal) {
        String id = UUID.randomUUID().toString();
        update(id, animal);
        return animal;
    }

    @Override
    public T get(String id) {
        return items.get(id);
    }
}
