package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.repository.AnimalRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private final Map<String, T> items = new ConcurrentHashMap<>();

    private final Logger log = Logger.getLogger(BaseService.class.getName());
    private AnimalRepository animalRepository;

    @Autowired
    public BaseService(AnimalRepository animalRepository) {

        this.animalRepository = animalRepository;
    }


    @Override
    public Collection<T> all() {
        return items.values();
    }

    @Override
    public T create(T animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        animalRepository.save((BaseAnimal) animal);
        items.put(id, animal);
        return animal;
    }

    @Override
    public T get(String id) {
        return items.get(id);
    }

    @Override
    public Optional<T> delete(String id) {
        if (StringUtils.isBlank(id)) {
            log.fine("Can't delete object of blank/null id");
            return Optional.empty();
        };

        final T removed = items.remove(id);

        if (null == removed) {
            return Optional.empty();
        }

        animalRepository.delete((BaseAnimal) removed);
        return Optional.ofNullable(removed);
    }

    @Override
    public synchronized Optional<T> update(String id, T toUpdate) {
        if (StringUtils.isBlank(id)) {
            log.fine("Can't update object of blank/null id");
            return Optional.empty();
        };

        final T item = items.get(id);
        if (item == null) {
            log.fine("Can't update object of id that doesn't exist");
            return Optional.empty();
        }

        item.setName(toUpdate.getName());
        item.setDescription(toUpdate.getDescription());

        animalRepository.save((BaseAnimal) item);
        items.put(id, item);

        log.info("Updated item of id " + id);

        return Optional.ofNullable(item);
    }
}
