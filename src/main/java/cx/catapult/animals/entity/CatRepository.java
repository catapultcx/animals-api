package cx.catapult.animals.entity;

import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository<CatEntity, String> {
}
