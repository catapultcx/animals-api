package cx.catapult.animals.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import cx.catapult.animals.domain.Animal;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private Map<String, T> items = new ConcurrentHashMap<>();

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
	return get(animal.getId());
    }

    @Override
    public T remove(String id) {
	return items.remove(id);
    }
}
