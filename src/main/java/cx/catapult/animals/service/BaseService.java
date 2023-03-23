package cx.catapult.animals.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.exception.AnimalNotFoundException;

public abstract class BaseService<T extends Animal> implements Service<T> {

	private HashMap<String, T> items = new HashMap<>();

	@Override
	public Collection<T> all() {
		return items.values();
	}

	@Override
	public T create(T animal) {
		String id = UUID.randomUUID().toString();
		String name = animal.getName();
		String description = animal.getDescription();

		if (name.isBlank() || description.isBlank()) {
			throw new IllegalArgumentException("Name and description are required fields");
		}

		animal.setId(id);
		items.put(id, animal);
		return animal;
	}

	@Override
	public T get(String id) {
		T animal = items.get(id);
		if (animal == null) {
			throw new AnimalNotFoundException(id);
		}
		return animal;
	}

	@Override
	public void delete(String id) {
		T animal = items.remove(id);
		if (animal == null) {
			throw new AnimalNotFoundException(id);
		}
	}
	
	@Override
	public T update(String id, T animal) {
	    T existingAnimal = items.get(id);
	    if (existingAnimal == null) {
	        throw new AnimalNotFoundException(id);
	    }


	    animal.setId(existingAnimal.getId());
	    items.put(id, animal);
	    
	    return animal;
	}
	
	@Override
    public Collection<T> getByName(String name) {
        return items.values()
                .stream()
                .filter(a -> a.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<T> getByDescription(String description) {
        return items.values()
                .stream()
                .filter(a -> a.getDescription().equals(description))
                .collect(Collectors.toList());
    }
	
	
}
