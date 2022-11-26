package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@org.springframework.stereotype.Service
public class AnimalService implements Service {

    private final Map<String, Animal> items = new HashMap<>();

    @Override
    public Collection<Animal> all() {
        return items.values();
    }

    @Override
    public Animal create(Animal animal) {
        animal.setId(UUID.randomUUID().toString());
        items.put(animal.getId(), animal);
        return animal;
    }

    @Override
    public Animal get(String id) {
        return items.get(id);
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
