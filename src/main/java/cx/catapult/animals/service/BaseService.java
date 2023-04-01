package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.apache.commons.lang3.StringUtils;

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
    public boolean delete(String id) {
        if (items.containsKey(id)) {
            T removed = items.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(T animal) {
        String id = animal.getId();
        if (id != null && items.containsKey(id)) {
            T update = items.put(id, animal);
            return true;
        }
        return false;
    }

    @Override
    public Collection<T> search(String name, String description) {
        Collection<T> animals = all();
        Predicate<T> namePredicate = item -> StringUtils.containsIgnoreCase(item.getName(), name);
        Predicate<T> descPredicate = item -> StringUtils.containsIgnoreCase(item.getDescription(), description);
        Collection<T> filtered = animals.stream().filter(namePredicate).filter(descPredicate).collect(Collectors.toList());
        return filtered;
    }
}
