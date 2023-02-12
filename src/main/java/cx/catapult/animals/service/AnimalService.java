package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Type;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Predicate;

@Service
public class AnimalService implements IService {

    private HashMap<String, Animal> items = new HashMap<>();

    @PostConstruct
    public void initialize() {
        this.create(new Animal("Tom", "Friend of Jerry", "Black", "Cat"));
        this.create(new Animal("Jerry", "Not really a cat", "White", "Mouse"));
        this.create(new Animal("Bili", "Long Snake", "Stripe black and while", "Snake"));
        this.create(new Animal("Smelly", "Quicker on water", "Gold", "Fish"));
        this.create(new Animal("Tiger", "Large cat", "Yellow and Black Stripes", "Tiger"));
        this.create(new Animal("Croc", "Big and Scary", "grey", "Crocodile"));
        this.create(new Animal("Sharp eye", "Big bird", "Black and while", "Bird"));
    }

    @Override
    public Collection<Animal> all() {
        return items.values();
    }

    @Override
    public Animal create(Animal animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        items.put(id, animal);
        return animal;
    }

    @Override
    public Animal get(String id) {
        return items.get(id);
    }

    @Override
    public Animal update(Animal updateAnimal) {
        Animal animal = items.get(updateAnimal.getId());
        animal.setName(updateAnimal.getName());
        animal.setDescription(updateAnimal.getDescription());
        animal.setColour(updateAnimal.getColour());
        animal.setType(updateAnimal.getType());
        animal.setGroup(Type.valueOf(updateAnimal.getType().toUpperCase()).getGroup());
        items.put(updateAnimal.getId(), animal);
        return animal;
    }

    @Override
    public void delete(String id) {
        items.remove(id);
    }

    @Override
    public List<Animal> filter(String name, String description, String colour, String type) {
        Predicate<Map.Entry<String, Animal>> namePredicate = getNamePredicate(name);
        Predicate<Map.Entry<String, Animal>> descriptionPredicate = getDescriptionPredicate(description);
        Predicate<Map.Entry<String, Animal>> colourPredicate = getColourPredicate(colour);
        Predicate<Map.Entry<String, Animal>> typePredicate = getTypePredicate(type);
        return items.entrySet()
                .stream()
                .filter(namePredicate.and(descriptionPredicate).and(colourPredicate).and(typePredicate))
                .map(Map.Entry::getValue)
                .toList();
    }

    private Predicate<Map.Entry<String, Animal>> getTypePredicate(String type) {
        Predicate<Map.Entry<String, Animal>> typePredicate = animal -> StringUtils.isBlank(type) ||
                animal.getValue().getType().equalsIgnoreCase(type);
        return typePredicate;
    }

    private Predicate<Map.Entry<String, Animal>> getColourPredicate(String colour) {
        Predicate<Map.Entry<String, Animal>> colourPredicate = animal -> StringUtils.isBlank(colour) ||
                animal.getValue().getColour().equalsIgnoreCase(colour);
        return colourPredicate;
    }

    private Predicate<Map.Entry<String, Animal>> getDescriptionPredicate(String description) {
        Predicate<Map.Entry<String, Animal>> descriptionPredicate = animal -> StringUtils.isBlank(description) ||
                animal.getValue().getDescription().equalsIgnoreCase(description);
        return descriptionPredicate;
    }

    private Predicate<Map.Entry<String, Animal>> getNamePredicate(String name) {
        Predicate<Map.Entry<String, Animal>> namePredicate = animal -> StringUtils.isBlank(name) ||
                animal.getValue().getName().equalsIgnoreCase(name);
        return namePredicate;
    }
}
