package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Animal createAnimalForOwner(String ownerId, Animal animal) {
        return animalRepository.createAnimalForOwner(ownerId, animal);
    }

    @Override
    public Collection<Animal> getAllAnimalsForOwner(String ownerId) {
        return animalRepository.getAllAnimalsForOwner(ownerId);
    }

    @Override
    public Animal getAnimalForOwner(String ownerId, String animalId) {
        return animalRepository.getAnimalForOwner(ownerId, animalId);
    }
}
