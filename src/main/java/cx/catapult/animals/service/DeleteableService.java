package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

public interface DeleteableService<T extends Animal> extends Service<T> {
    T delete(T animal);
}
