package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.FilterOptions;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private final HashMap<String, T> items = new HashMap<>();

    @Override
    public Collection<T> all(FilterOptions filterOptions) {
        Collection<T> all = items.values();
        if (filterOptions == null) {
            return all;
        }

        if (StringUtils.hasText(filterOptions.getType())) {
            all = all.stream().filter(item -> filterOptions.getType().equalsIgnoreCase(item.getType())).collect(Collectors.toList());
        }

        if (StringUtils.hasText(filterOptions.getName())) {
            all = all.stream().filter(item -> filterOptions.getName().equalsIgnoreCase(item.getName())).collect(Collectors.toList());
        }

        if (StringUtils.hasText(filterOptions.getColour())) {
            all = all.stream().filter(item -> filterOptions.getColour().equalsIgnoreCase(item.getColour())).collect(Collectors.toList());
        }

        if (StringUtils.hasText(filterOptions.getDescription())) {
            all = all.stream().filter(item -> filterOptions.getDescription().equalsIgnoreCase(item.getDescription())).collect(Collectors.toList());
        }

        return all;
    }

    @Override
    public T create(T animal) {
        if (animal.getId() == null) {
            String id = UUID.randomUUID().toString();
            animal.setId(id);
        }
        items.put(animal.getId(), animal);
        return animal;
    }

    @Override
    public T update(String id, T animal) {
        T currentItem = items.get(id);
        if (currentItem == null) {
            return null;
        }
        items.put(id, animal);
        return animal;
    }

    @Override
    public T get(String id) {
        return items.get(id);
    }

    public T delete(String id) {
        if (items.get(id) != null) {
            return items.remove(id);
        }
        return null;
    }
}
