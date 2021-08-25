package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private final Map<String, T> items = new ConcurrentHashMap<>();

    private final Logger log = Logger.getLogger(BaseService.class.getName());

    @Override
    public Collection<T> all() {
        return items.values();
    }

    @Override
    public T create(T animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
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

        return Optional.ofNullable(items.remove(id));
    }
}
