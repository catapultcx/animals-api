package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;
import java.util.stream.Collectors;


public abstract class BaseService<T extends Animal> implements Service<T> {

    private HashMap<String, T> items = new HashMap<>();

    @Override
    public Collection<T> all(String keyWords) {
        if (keyWords == null || keyWords.length() == 0) {
            return items.values();    
        }

        return items.values().stream().filter(item -> {
            String name = item.getName();
            String description = item.getDescription();
            return name.toLowerCase().contains(keyWords.toLowerCase()) || description.toLowerCase().contains(keyWords.toLowerCase());  
        }).collect(Collectors.toList());
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
        items.put(id, animal);
        return items.get(id);
    }

    @Override
    public T get(String id) {
        return items.get(id);
    }

    @Override
    public Collection<T> delete(String id) {
        items.remove(id);
        return items.values();
    }
}
