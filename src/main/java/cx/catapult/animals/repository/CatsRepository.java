package cx.catapult.animals.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cx.catapult.animals.domain.Cat;

@Repository
public interface CatsRepository extends AnimalsRepository<Cat> {

	@Query("from Cat")
	List<Cat> getAll();
}
