package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Group;

import java.util.*;

public class AnimalService {

    private final String animalType;
    private final HashMap<String, Animal> items = new HashMap<>();
    private final Group group;

    public AnimalService(String animalType, Group group) {
        this.animalType = animalType;
        this.group = group;
    }

    public String getAnimalType() {
        return animalType;
    }

    public Collection<Animal> all() {
        return items.values();
    }

    public Animal create(Animal animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        animal.setType(this.animalType);
        animal.setGroup(this.group);
        items.put(id, animal);
        return animal;
    }

    public Animal get(String id) {
        return items.get(id);
    }

    public boolean delete(String id) {
        if (items.containsKey(id)) {
            items.remove(id);
            return true;
        }
        return false;
    }

    public Animal update(String id, Animal animal) {
        if (!items.containsKey(id))
            return null;
        items.put(id, animal);
        return items.get(id);
    }

    public List<Animal> filter(
            Optional<List<String>> names,
            Optional<List<String>> colors,
            Optional<List<String>> descriptions
    ) {
        return items
                .values()
                .stream()
                .filter(
                        data -> filterByName(names, data)
                                && filterByColor(colors, data)
                                && filterByDescription(descriptions, data)
                )
                .toList();
    }

    private boolean filterByName(Optional<List<String>> query, Animal data) {
        return queryEquals(query, data.getName());
    }

    private boolean filterByColor(Optional<List<String>> query, Animal data) {
        return queryEquals(query, data.getColor());
    }

    private boolean filterByDescription(Optional<List<String>> query, Animal data) {
        return queryEquals(query, data.getDescription());
    }

    private static boolean queryEquals(Optional<List<String>> query, String data) {
        return query.isEmpty()
                || query.get().isEmpty()
                || query.get().stream().anyMatch(data::contains);
    }
}
