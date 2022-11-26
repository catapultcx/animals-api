package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.Collection;

public interface Service {

    public Collection<Animal> all();

    Animal create(Animal animal);

    public Animal get(String id);

}
