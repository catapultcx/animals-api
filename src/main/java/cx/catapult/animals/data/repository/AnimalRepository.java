package cx.catapult.animals.data.repository;

import cx.catapult.animals.data.entity.AnimalEntity;
import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository<T extends Animal> extends JpaRepository<AnimalEntity, Long> {
    AnimalEntity findOneByAnimalId(String id);

    void deleteByAnimalId(String id);
}
