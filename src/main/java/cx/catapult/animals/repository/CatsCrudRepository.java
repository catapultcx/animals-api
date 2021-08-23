package cx.catapult.animals.repository;

import cx.catapult.animals.entity.CatEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatsCrudRepository extends CrudRepository<CatEntity, String> {

}
