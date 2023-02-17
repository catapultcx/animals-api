package cx.catapult.animals.repository;

import cx.catapult.animals.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AnimalRepository extends JpaRepository<AnimalEntity, String>, JpaSpecificationExecutor<AnimalEntity> {
    List<AnimalEntity> findAllByTypeIn(List<String> types);
}
