package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;

public interface Service<T extends Animal> {

    public Collection<T> all();

    T create(T animal);

    public T get(String id);
    
    void delete(String id);
    
    public T update(String id, T animal);
    
    public Collection<T> getByName(String name);
    
    public Collection<T> getByDescription(String description);

}
