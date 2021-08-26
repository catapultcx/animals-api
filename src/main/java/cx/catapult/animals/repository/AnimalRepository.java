package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<BaseAnimal, String> {

}
