package cx.catapult.animals.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cx.catapult.animals.domain.Crustacean;

@Repository
public interface CrustaceansRepository extends AnimalsRepository<Crustacean> {

	@Query("from Crustacean")
	List<Crustacean> getAll();
}
