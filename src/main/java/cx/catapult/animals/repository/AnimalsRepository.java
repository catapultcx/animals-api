package cx.catapult.animals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cx.catapult.animals.domain.BaseAnimal;

@Repository
public interface AnimalsRepository<T extends BaseAnimal> extends JpaRepository<T, String> {
}
