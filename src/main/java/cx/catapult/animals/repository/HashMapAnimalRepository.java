package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Animal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Repository
public class HashMapAnimalRepository implements AnimalRepository {

    private final Map<String, Collection<Animal>> animals;

    {
        this.animals = new HashMap<>();
        animals.put("TEST", List.of(new Animal("1234", "cat", "Tom", "Red", "Kitty cat")));
    }

    @Override
    public Animal createAnimalForOwner(String ownerId, Animal animal) {
        final var animalId = UUID.randomUUID().toString();
        var toPersist = new Animal(animalId, animal.type(), animal.name(), animal.colour(), animal.description());
        if (ownerIdExists(ownerId)) {
            animals.get(ownerId).add(toPersist);
        } else {
            Collection<Animal> animalList = new ArrayList<>();
            animalList.add(toPersist);
            animals.put(ownerId, animalList);
        }
        return getAnimalForOwner(ownerId, animalId);
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

    @Override
    public void removeAnimalForOwner(String ownerId, String animalId) {
        if (ownerIdExists(ownerId)) {
            var toRemove = getAnimalForOwner(ownerId, animalId);
            this.animals.get(ownerId).remove(toRemove);
        } else {
            throw new AnimalRepositoryException(format("Failed to remove animal: %s It doesn't exist in the database", animalId));
        }
    }

    @Override
    public Animal updateAnimalForOwner(String ownerId, Animal animal) {
        if(animal.id() == null){
            throw new AnimalRepositoryException(format("For owner id: %s. animal id can't be null", ownerId));
        }
        var animalsForId = getAnimalsForOwner(ownerId, animal);

        Optional<Animal> toRemove = animalsForId.stream()
            .filter(storedEvent ->
                storedEvent.id().equals(animal.id())
            ).findFirst();

        if (toRemove.isPresent()) {
            animals.get(ownerId).remove(toRemove.get());
            animals.get(ownerId).add(animal);
        }

        return getAnimalForOwner(ownerId, animal.id());
    }

    private Collection<Animal> getAnimalsForOwner(String ownerId, Animal animal) {
        Collection<Animal> animalsForId;
        if (ownerIdExists(ownerId)) {
            animalsForId = animals.get(ownerId);
        } else {
            throw new AnimalRepositoryException(format("Owner id: %s does not exist. Can not update animal: %s", ownerId, animal.id()));
        }
        return animalsForId;
    }

    private boolean ownerIdExists(String ownerId) {
        return animals.containsKey(ownerId);
    }

}
