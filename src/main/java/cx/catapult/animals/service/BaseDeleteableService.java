package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

public class BaseDeleteableService<T extends Animal> extends BaseService<T> implements DeleteableService<T> {
    @Override
    public T delete(final T animal) {
        return items.remove(animal.getId());
    }
}
