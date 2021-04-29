package cx.catapult.animals.service;

import java.util.Collection;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.repository.CrustaceansRepository;

@Service
public class CrustaceansService extends PersistenceService<Crustacean> {

	private final CrustaceansRepository repository;

	public CrustaceansService(CrustaceansRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@PostConstruct
	public void initialize() {
		this.create(new Crustacean("Crusty", "Crusty the crab"));
		this.create(new Crustacean("Libby", "Libby the Lobster"));
		this.create(new Crustacean("Paula", "Paula the Prawn"));
	}

	@Override
	public Collection<Crustacean> all() {
		return repository.getAll();
	}
}
