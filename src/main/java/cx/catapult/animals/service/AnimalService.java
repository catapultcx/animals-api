package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Type;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

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
}
