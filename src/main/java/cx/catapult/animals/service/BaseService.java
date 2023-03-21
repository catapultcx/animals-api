package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private HashMap<String, T> items = new HashMap<>();

    @Override
    public Collection<T> all() {
        return items.values();
    }

    @Override
    public Collection<T> all(String name, String description) {
        List<Predicate<T>> predicates = new ArrayList<>();

        predicates.add(animal -> animal.getName().equals(name));
        predicates.add(animal -> animal.getDescription().equals(description));

        Predicate<T> predicate = predicates
                .stream()
                .reduce(Predicate::and)
                .get();

        return items
                .values()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
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
    public T delete(String id) {
        return items.remove(id);
    }

    @Override
    public T get(String id) {
        return items.get(id);
    }
}
