package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import java.util.Optional;

public interface SearchService {
    Optional<Animal> searchById(final String id);
    Optional<Animal> searchByName(final String name);
}
