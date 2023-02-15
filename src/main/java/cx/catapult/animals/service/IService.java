package cx.catapult.animals.service;

import cx.catapult.animals.domain.IAnimal;

import java.util.Collection;

public interface IService<T extends IAnimal> {

    public Collection<T> all();

    public T get(String id);

    public T create(T animal);

    public T update(T animal);

    public void delete(String id);

}
