package cx.catapult.animals.repository;

import cx.catapult.animals.domain.PersistentCrustacean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrustaceanRepository extends CrudRepository<PersistentCrustacean, String> {}
