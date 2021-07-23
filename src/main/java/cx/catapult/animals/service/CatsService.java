package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Group;
import cx.catapult.animals.repository.AnimalRepository;
import cx.catapult.animals.repository.entity.AnimalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static cx.catapult.animals.domain.BaseAnimal.CAT_STRING;

@Service
public class CatsService extends BaseService<Cat> {

    @Autowired
    public CatsService(final AnimalRepository animalRepository) {
        super(animalRepository);
    }

    @Override
    public Collection<Cat> all() {
        return convertedList(animalRepository.findByAnimalType(CAT_STRING));
    }

    @Override
    Cat convertEntityToDomainObject(AnimalEntity animalEntity) {
        return Cat.builder()
                .description(animalEntity.getDescription())
                .id(animalEntity.getId().toString())
                .name(animalEntity.getName())
                .group(Group.toGroup(animalEntity.getGroup()))
                .build();
    }

    @Override
    AnimalEntity convertDomainObjectToEntity(Cat animal) {
        return AnimalEntity.builder()
                .animalType(CAT_STRING)
                .group(animal.getGroup().name())
                .description(animal.getDescription())
                .name(animal.getName())
                .build();
    }

}
