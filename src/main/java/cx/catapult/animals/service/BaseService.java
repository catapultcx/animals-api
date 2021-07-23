package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.exception.AnimalNotFoundException;
import cx.catapult.animals.repository.AnimalRepository;
import cx.catapult.animals.repository.entity.AnimalEntity;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class BaseService<T extends Animal> implements Service<T> {

    protected final AnimalRepository animalRepository;

    protected List<T> convertedList(List<AnimalEntity> animals) {
        return animals.stream().map(this::convertEntityToDomainObject).collect(Collectors.toList());
    }


    @Override
    public T create(T animal) {
        AnimalEntity animalEntity = convertDomainObjectToEntity(animal);
        AnimalEntity savedAnimalEntity = animalRepository.save(animalEntity);
        return convertEntityToDomainObject(savedAnimalEntity);
    }

    @Override
    public T get(String id) {
        Optional<AnimalEntity> animalEntityOpt = animalRepository.findById(convertIdToLong(id));
        return animalEntityOpt.map(this::convertEntityToDomainObject).orElseThrow( () -> new AnimalNotFoundException());
    }

    @Override
    public void delete(String id) {
        try {
            animalRepository.deleteById(convertIdToLong(id));
        } catch (EmptyResultDataAccessException erdae) {
            throw new AnimalNotFoundException();
        }
    }

    @Override
    public void update(T animal) {
        Optional<AnimalEntity> animalEntityOpt = animalRepository.findById(convertIdToLong(animal.getId()));
        if(animalEntityOpt.isPresent()) {
            this.updateAnimalEntityAndSave(animal, animalEntityOpt.get());
        } else {
            throw new AnimalNotFoundException();
        }
    }

    abstract T convertEntityToDomainObject(AnimalEntity animalEntity);
    abstract AnimalEntity convertDomainObjectToEntity(T animal);

    private void updateAnimalEntityAndSave(T animal, AnimalEntity animalEntity) {
        animalEntity.setDescription(animal.getDescription());
        animalEntity.setName(animal.getName());
        animalEntity.setUpdatedDate(LocalDateTime.now());
        animalRepository.save(animalEntity);
    }

    private Long convertIdToLong(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException nfe) {
            throw new AnimalNotFoundException();
        }
    }
}
