package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Group;
import cx.catapult.animals.entity.AnimalEntity;
import cx.catapult.animals.repository.AnimalRepository;

import java.util.*;

public class AnimalService {

    private final String animalType;
    private final HashMap<String, Animal> items = new HashMap<>();
    private final Group group;
    private final AnimalRepository repository;

    public AnimalService(String animalType, Group group, AnimalRepository repository) {
        this.animalType = animalType;
        this.group = group;
        this.repository = repository;
    }

    public String getAnimalType() {
        return animalType;
    }

    public Collection<Animal> all() {
        return Animal.fromEntity(repository.findAllByTypeIn(List.of(this.animalType)));
    }

    public Animal create(Animal animal) {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setColor(animal.getColor());
        animalEntity.setDescription(animal.getDescription());
        animalEntity.setName(animal.getName());
        animalEntity.setType(this.animalType);
        animalEntity.setGroup(this.group);
        return Animal.fromEntity(repository.save(animalEntity));
    }

    public Animal get(String id) {
        return repository.findById(id).map(Animal::fromEntity).orElse(null);
    }

    public boolean delete(String id) {
        if(id == null) return false;
        Optional<AnimalEntity> entity = repository.findById(id);
        if (entity.isPresent()){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Animal update(String id, Animal animal) {
        Optional<AnimalEntity> entity = repository.findById(id);
        if (entity.isEmpty())
            return null;
        AnimalEntity animalEntity = entity.get();
        animalEntity.setColor(animal.getColor());
        animalEntity.setDescription(animal.getDescription());
        animalEntity.setName(animal.getName());
        return Animal.fromEntity(repository.save(animalEntity));
    }

}
