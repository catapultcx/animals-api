package cx.catapult.animals.repository;

import cx.catapult.animals.repository.entity.AnimalEntity;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CatsRepository extends CrudRepository<AnimalEntity, Long> {

    Iterable<AnimalEntity> findAll();

    Optional<AnimalEntity> findById(String userId);

}
