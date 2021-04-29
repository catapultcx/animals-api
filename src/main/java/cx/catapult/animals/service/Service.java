package cx.catapult.animals.service;

import java.util.Collection;

import cx.catapult.animals.domain.Animal;

/**
 * Interface defining the common service operations on any type of {@link Animal}.
 *
 * @param <T> The type of the {@link Animal} in question.
 */
public interface Service<T extends Animal> {

	/**
	 * Retrieves all of the available instances of a particular type of {@link Animal}.
	 *
	 * @return {@link Collection} of the {@link Animal}.
	 */
    Collection<T> all();

	/**
	 * Persists the provided {@link Animal} instance.
	 *
	 * @param animal The {@link Animal} to persist.
	 */
    T create(T animal);

	/**
	 * Retrieves the desired {@link Animal} by its ID.
	 *
	 * @return The requested {@link Animal} or {@code null} if it doesn't exist.
	 */
    T get(String id);

	/**
	 * Deletes an {@link Animal} by its ID.
	 *
	 * @param id The ID of the {@link Animal} to delete.
	 * @return {@code true} if the {@link Animal} is deleted successfully.
	 */
	boolean delete(String id);
}
