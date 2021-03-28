package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private final StorageService<T> service;

    protected BaseService(final StorageService service) {
        this.service = service;
    }

    @Override
    public Collection<T> all() {
        return service.all();
    }

    @Override
    public T create(T animal) {
        return (T) service.create(animal);
    }

    @Override
    public T get(String id) {
        return (T) service.get(id);
    }

    @Override
    public void delete(final String id) {
        service.delete(id);
    }

    @Override
    public T update(final String id,
                    final T newAnimal) {
        return (T) service.update(id, newAnimal);
    }
}
