package cx.catapult.animals.repository;

import cx.catapult.animals.entity.CatEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository<CatEntity, UUID> {
    Optional<CatEntity> findById(UUID id);
}
