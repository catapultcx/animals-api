package cx.catapult.animals.repository;

import cx.catapult.animals.repository.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {
    List<AnimalEntity> findByAnimalType(String animalType);
}
