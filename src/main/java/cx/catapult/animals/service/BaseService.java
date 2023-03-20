package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.stream.Collectors;

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
    public T delete(String id) {
        return items.remove(id);
    }

    @Override
    public T update(T animal) {
        items.put(animal.getId(), animal);
        return animal;
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
