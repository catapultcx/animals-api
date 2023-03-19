package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private final HashMap<String, T> items = new LinkedHashMap<>();

    private HashMap<String, T> getItems() {
        return this.items;
    }

    @Override
    public Collection<T> all() {
        return getItems().values();
    }

    @Override
    public Collection<T> filterBy(final String query) {
        if (StringUtils.isEmpty(query)) {
            return all();
        }

        final String queryLowerCase = query.toLowerCase();
        return getItems().values().stream()
                .filter(item ->
                        item.getName().toLowerCase().contains(queryLowerCase) ||
                                item.getDescription().toLowerCase().contains(queryLowerCase))
                .collect(Collectors.toList());
    }

    @Override
    public T create(T animal) {
        animal.setId(UUID.randomUUID().toString());
        return saveAndReturn(animal);
    }

    @Override
    public T get(String id) {
        return getItems().get(id);
    }

    @Override
    public T delete(String id) {
        return getItems().remove(id);
    }

    @Override
    public T update(String id, T animal) {
        animal.setId(id);
        return saveAndReturn(animal);
    }

    private T saveAndReturn(T animal) {
        getItems().put(animal.getId(), animal);
        return get(animal.getId());
    }
}
