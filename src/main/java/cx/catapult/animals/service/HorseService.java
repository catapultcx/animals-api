package cx.catapult.animals.service;

import cx.catapult.animals.domain.Group;
import cx.catapult.animals.domain.Horse;
import cx.catapult.animals.repository.AnimalRepository;
import cx.catapult.animals.repository.entity.AnimalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static cx.catapult.animals.domain.BaseAnimal.HORSE_STRING;

@Service
public class HorseService extends BaseService<Horse> {

    @Autowired
    public HorseService(AnimalRepository animalRepository) {
        super(animalRepository);
    }

    @Override
    Horse convertEntityToDomainObject(AnimalEntity animalEntity) {
        return Horse.builder()
                .description(animalEntity.getDescription())
                .id(animalEntity.getId().toString())
                .name(animalEntity.getName())
                .group(Group.toGroup(animalEntity.getGroup()))
                .build();
    }

    @Override
    AnimalEntity convertDomainObjectToEntity(Horse animal) {
        return AnimalEntity.builder()
                .animalType(HORSE_STRING)
                .group(animal.getGroup().name())
                .description(animal.getDescription())
                .name(animal.getName())
                .build();
    }

    @Override
    public Collection<Horse> all() {
        return convertedList(animalRepository.findByAnimalType(HORSE_STRING));
    }
}
