package cx.catapult.animals.service;

import java.util.Collection;
import cx.catapult.animals.domain.AnimalIf;

public interface Service<T extends AnimalIf> {

    Collection<T> filter(String name, String description, String colour, String type);

    T get(String id);

    T create(T animal);

    T update(T animal);

    void delete(String id);
}
