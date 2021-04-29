package cx.catapult.animals.service;

import java.util.Collection;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CatsRepository;

@Service
public class CatsService extends PersistenceService<Cat> {

	private final CatsRepository repository;

	public CatsService(CatsRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@PostConstruct
    public void initialize() {
        this.create(new Cat("Tom", "Friend of Jerry"));
        this.create(new Cat("Jerry", "Not really a cat"));
        this.create(new Cat("Bili", "Furry cat"));
        this.create(new Cat("Smelly", "Cat with friends"));
        this.create(new Cat("Tiger", "Large cat"));
        this.create(new Cat("Tigger", "Not a scary cat"));
        this.create(new Cat("Garfield", "Lazy cat"));
    }

	@Override
	public Collection<Cat> all() {
		// not sure what should happen here - retrieve from memory or db?
		return repository.getAll();
	}
}
