package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class AnimalService implements Service {

    private final Map<String, Animal> animals = new HashMap<>();

    @Override
    public Collection<Animal> all() {
        return animals.values();
    }

    @Override
    public Animal create(Animal animal) {
        animal.setId(UUID.randomUUID().toString());
        animals.put(animal.getId(), animal);
        return animal;
    }

    @Override
    public Animal get(String id) {
        return animals.get(id);
    }

    @Override
    public Animal delete(String id) {
        //TODO Handle scenario, when animal with given id not exist
        return animals.remove(id);
    }

    @Override
    public Animal update(String id, Animal animalToUpdate) {
        //TODO Handle scenario, when animal with given id not exist
        animalToUpdate.setId(id);
        animals.put(id, animalToUpdate);
        return animalToUpdate;
    }

    @Override
    public List<Animal> filter(String searchTerm) {
        return animals.values().stream()
                .filter(hasAnimalMatched(searchTerm))
                .collect(Collectors.toList());
    }

    private static Predicate<Animal> hasAnimalMatched(String searchTerm) {
        var lowerCaseSearchTerm = searchTerm.toLowerCase();
        return animal ->
                animal.getName().toLowerCase().contains(lowerCaseSearchTerm) ||
                        animal.getDescription().toLowerCase().contains(lowerCaseSearchTerm) ||
                        animal.getType().name().toLowerCase().contains(lowerCaseSearchTerm) ||
                        animal.getColour().toLowerCase().contains(lowerCaseSearchTerm);
    }

    @PostConstruct
    public void initialize() {
        this.create(Animal.aCat("Tom", "Friend of Jerry", "orange"));
        this.create(Animal.aCat("Jerry", "Not really a cat", "white"));
        this.create(Animal.aCat("Bili", "Furry cat", "black"));
        this.create(Animal.anEagle("Eagle-1", "1st Eagle", "red"));
        this.create(Animal.anEagle("Eagle-2", "2nd Eagle", "black"));
        this.create(Animal.anEagle("Eagle-3", "3rd Eagle", "yellow"));
    }
}
