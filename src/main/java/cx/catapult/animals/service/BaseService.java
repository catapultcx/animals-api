package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public abstract class BaseService<T extends Animal> implements Service<T> {

    HashMap<String, T> items = new HashMap<>();

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
        T animal = items.get(id);
        if (animal == null){
            return null;
        }
        if (null == animal.getId()) {
            animal.setId(id);
        }
        return animal;
    }

    public T update(String id, T animal) {
        items.replace(id, animal);
        return get(id);
    }
}
