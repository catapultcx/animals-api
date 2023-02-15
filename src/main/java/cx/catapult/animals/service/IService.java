package cx.catapult.animals.service;

import cx.catapult.animals.domain.IAnimal;

import java.util.Collection;

public interface IService<T extends IAnimal> {

    public Collection<T> all();

    T create(T animal);

    public T get(String id);

}
