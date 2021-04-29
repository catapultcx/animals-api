package cx.catapult.animals.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.repository.AnimalsRepository;

/**
 * Extends the in-memory functionality of the {@link BaseService} by providing persistent database storage
 * and retrieval.
 *
 * <p>Acceptance Criteria: Must be [sic] keep existing in memory backend (BaseService) in the code.</p>
 *
 * <p>Does this mean items should still be stored in memory? If so, is the (non thread safe) memory storage
 * meant to act as a cache? This is my interpretation of the requirements, but I would never recommend
 * implementing a cache this way!
 *
 * @param <T> The type of the {@link BaseAnimal} in question.
 */
public abstract class PersistenceService<T extends BaseAnimal> extends BaseService<T> {

	private static final Logger logger = LoggerFactory.getLogger(PersistenceService.class);

	private final AnimalsRepository<T> repository;

	public PersistenceService(AnimalsRepository<T> repository) {
		this.repository = repository;
	}

	@Override
	public T create(T animal) {
		// generate its id and insert into memory, before persisting in the db
		super.create(animal);

		repository.save(animal);
		return animal;
	}

	@Override
	public T get(String id) {
		// first get it from memory
		T cat = super.get(id);

		if (cat == null) {
			cat = repository.findById(id).orElse(null);
		}
		return cat;
	}

	@Override
	public T update(String id, T updatedAnimal) {
		// update the instance in memory and then the db
		super.update(id, updatedAnimal);

		T existingAnimal = repository.findById(id).orElse(null);
		if (existingAnimal != null) {
			existingAnimal.setName(updatedAnimal.getName());
			existingAnimal.setDescription(updatedAnimal.getDescription());
			repository.save(existingAnimal);
		}

		return existingAnimal;
	}

	@Override
	public boolean delete(String id) {
		// remove from memory and then the db
		super.delete(id);

		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			logger.warn("Failed to delete Animal with id [{}]", id);
			return false;
		}
		return true;
	}
}
