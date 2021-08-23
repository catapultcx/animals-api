package cx.catapult.animals.repository;

import cx.catapult.animals.entity.BirdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirdsCrudRepository extends CrudRepository<BirdEntity, String> {

}
