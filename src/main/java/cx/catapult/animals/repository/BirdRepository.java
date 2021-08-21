package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Bird;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface BirdRepository extends CrudRepository<Bird, String>, JpaSpecificationExecutor<Bird> {
}
