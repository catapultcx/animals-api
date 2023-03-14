package cx.catapult.animals.service;

import static java.util.Objects.isNull;

import cx.catapult.animals.domain.Animal;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Predicate;
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
    public T update(String id, T animal) {
        animal.setId(id);
        return items.put(id, animal);
    }

    @Override
    public Collection<T> find(String name, String description) {
        if (isNull(name) && isNull(description)) {
            return all();
        }

        Predicate<T> condition1 = (cat) -> isNull(name) ? true
            : cat.getName().toLowerCase().contains(name.toLowerCase());
        Predicate<T> condition2 = (cat) -> isNull(description) ? true
            : cat.getDescription().toLowerCase().contains(description.toLowerCase());

        return items.values().stream()
            .filter(cat -> condition1.and(condition2).test(cat))
            .collect(Collectors.toList());
    }
}
