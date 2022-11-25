package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Animal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class HashMapAnimalRepository implements AnimalRepository {

    private final Map<String, Collection<Animal>> animals;

    {
        this.animals = new HashMap<>();
        animals.put("TEST", List.of(new Animal("1234", "cat", "Tom", "Red", "Kitty cat")));
    }

    @Override
    public Animal createAnimalForOwner(String ownerId, Animal animal) {
        var toPersist = new Animal(UUID.randomUUID().toString(), animal.type(), animal.name(), animal.colour(), animal.description());
        if (ownerIdExists(ownerId)) {
            animals.get(ownerId).add(toPersist);
            return toPersist;
        } else {
            Collection<Animal> animalList = new ArrayList<>();
            animalList.add(toPersist);
            animals.put(ownerId, animalList);
            return toPersist;
        }
    }

    @Override
    public Collection<Animal> getAllAnimalsForOwner(String ownerId) {
        return animals.get(ownerId);
    }

    @Override
    public Animal getAnimalForOwner(String ownerId, String animalId) {
        return animals.get(ownerId).stream()
            .filter(a -> a.id().equals(animalId))
            .findFirst()
            .orElseThrow(() -> new AnimalRepositoryException("Could not find animal id"));
    }

    private boolean ownerIdExists(String ownderId) {
        return animals.containsKey(ownderId);
    }

}
