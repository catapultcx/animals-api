package cx.catapult.animals.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import cx.catapult.animals.domain.Animal;

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
	public T update(String id, T updatedAnimal) {
		T existingAnimal = get(id);
		if (existingAnimal == null) {
			return null;
		}

		existingAnimal.setName(updatedAnimal.getName());
		existingAnimal.setDescription(updatedAnimal.getDescription());
		return existingAnimal;
	}

	@Override
	public boolean delete(String id) {
		return items.remove(id) != null;
	}
}
