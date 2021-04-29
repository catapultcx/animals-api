package cx.catapult.animals.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.repository.AnimalsRepository;

/**
 * Service for retrieving all types of {@link BaseAnimal} types.
 */
@Service
public class AnimalsService<T extends BaseAnimal> {

	private final AnimalsRepository<T> repository;

	public AnimalsService(@Qualifier("animalsRepository") AnimalsRepository<T> repository) {
		this.repository = repository;
	}

	public T get(String id) {
		return repository.findById(id).orElse(null);
	}

	public Collection<T> getByName(String name) {
		return repository.findByName(name);
	}
}
