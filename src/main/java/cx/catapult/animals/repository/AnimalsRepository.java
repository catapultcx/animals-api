package cx.catapult.animals.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cx.catapult.animals.domain.BaseAnimal;

@Repository
public interface AnimalsRepository<T extends BaseAnimal> extends JpaRepository<T, String> {

	@Query(
			value = "select * from animal u where u.id = ?1",
			nativeQuery = true
	)
	Optional<T> findById(String id);

	@Query(
			value = "select * from animal u where u.name = ?1",
			nativeQuery = true
	)
	List<T> findByName(String name);
}
