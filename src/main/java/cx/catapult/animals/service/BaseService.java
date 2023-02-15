package cx.catapult.animals.service;

import cx.catapult.animals.domain.IAnimal;
import cx.catapult.animals.domain.Type;
import cx.catapult.animals.exception.OperationNotAllowedException;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class BaseService<T extends IAnimal> implements IService<T> {

    private Map<String, T> items = new ConcurrentHashMap<>();

    @Override
    public Collection<T> filter(String name, String description, String colour, String type) {
        return items.values()
                .stream()
                .filter(
                        searchByName(name)
                                .and(searchByDescription(description))
                                .and(searchByColour(colour))
                                .and(searchByType(type))
                ).collect(Collectors.toList());
    }

    @Override
    public T get(String id) {
        if (items.containsKey(id)) {
            return items.get(id);
        } else {
            throw new OperationNotAllowedException(String.format("Animal not found for id: %s", id));
        }
    }

    @Override
    public T create(T animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        items.put(id, animal);
        return animal;
    }

    @Override
    public T update(T animal) {
        T existingAnimal = items.get(animal.getId());
        if (existingAnimal == null) throw new OperationNotAllowedException(String.format("Animal not found for id: %s", animal.getId()));

        existingAnimal.setName(animal.getName());
        existingAnimal.setDescription(animal.getDescription());
        existingAnimal.setColour(animal.getColour());
        existingAnimal.setType(animal.getType());
        return items.get(animal.getId());
    }

    @Override
    public void delete(String id) {
        if (items.containsKey(id)) {
            items.remove(id);
        } else {
            throw new OperationNotAllowedException(String.format("Animal not found for id: %s", id));
        }
    }

    private Predicate<T> searchByName(String name) {
        return e -> Strings.isBlank(name) || e.getName().contains(name);
    }

    private Predicate<T> searchByDescription(String description) {
        return e -> Strings.isBlank(description) || e.getDescription().contains(description);
    }

    private Predicate<T> searchByColour(String colour) {
        return e -> Strings.isBlank(colour) || e.getColour().contains(colour);
    }

    private Predicate<T> searchByType(String type) {
        return e -> Strings.isBlank(type) || e.getType().equals(Type.get(type));
    }
}
