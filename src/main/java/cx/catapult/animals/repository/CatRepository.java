package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Cat;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository<Cat, String>, JpaSpecificationExecutor<Cat> {
}
