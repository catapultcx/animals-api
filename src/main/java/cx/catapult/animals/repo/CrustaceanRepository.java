package cx.catapult.animals.repo;

import cx.catapult.animals.domain.Crustacean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrustaceanRepository extends JpaRepository<Crustacean, Long> {}
