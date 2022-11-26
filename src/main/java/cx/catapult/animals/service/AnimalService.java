package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Service
public class AnimalService implements Service {

    private final Map<String, Animal> items = new HashMap<>();

    @Override
    public Collection<Animal> all() {
        return items.values();
    }

    @Override
    public Animal create(Animal animal) {
        items.put(animal.getId(), animal);
        return animal;
    }

    @Override
    public Animal get(String id) {
        return items.get(id);
    }

    @PostConstruct
    public void initialize() {
        this.create(Animal.aCat("Tom", "Friend of Jerry"));
        this.create(Animal.aCat("Jerry", "Not really a cat"));
        this.create(Animal.aCat("Bili", "Furry cat"));
        this.create(Animal.anEagle("Eagle-1", "1st Eagle"));
        this.create(Animal.anEagle("Eagle-2", "2nd Eagle"));
        this.create(Animal.anEagle("Eagle-3", "3rd Eagle"));
    }
}
