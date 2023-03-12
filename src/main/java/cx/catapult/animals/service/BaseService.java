package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

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
    public T update(T animal){
        if( items.containsKey(animal.getId()) )
            items.put(animal.getId(),animal);
        return animal;
    }

    @Override
    public boolean delete(String id){
        if(!items.containsKey(id))
            return false;

        items.remove(id);
        return true;
    }

    /**
     * This method filter to cat lists.
     * Parameter name is searches by the beginning of cats names.
     * Parameter description is searches by the sentence contains that parameter.
     * @param name
     * @param description
     * @return Collection<T>
     */
    @Override
    public Collection<T> getByFilter(String name, String description){

        return items.entrySet().stream()
            .filter(entry ->
                entry.getValue().getName().toLowerCase().startsWith(name.toLowerCase())
                    && entry.getValue().getDescription().toLowerCase().contains(description.toLowerCase())
            )
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }
}
