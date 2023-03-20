package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;

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
    public void remove(String id) {
        items.computeIfPresent(id, (k, v) -> {
            items.remove(id);
            return v;
        });
    }

    @Override
    public T update(T animal) {
        T result = items.computeIfPresent(animal.getId(), (key, value) -> {
            if (value == null) {
                return null;
            }
            T updatedValue = (T) value;
            updatedValue.setName(animal.getName());
            updatedValue.setDescription(animal.getDescription());
            return updatedValue;
        });
        return result;
    }

    @Override
    public Collection<T> filter(final String text) {
        if (Strings.isBlank(text))
            return all();
        String textLower = text.toLowerCase();
        return items.values().stream()
                .filter(c -> c.getName().toLowerCase().contains(textLower)
                        || c.getDescription().toLowerCase().contains(textLower))
                .collect(Collectors.toList());
    }

}
