package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Bird;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BirdRepository extends CrudRepository<Bird, String>, JpaSpecificationExecutor<Bird> {
    Optional<Bird> findByName(@Param("name") String name);
}
