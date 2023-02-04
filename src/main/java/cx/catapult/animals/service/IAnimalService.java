package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;

import java.util.Collection;

public interface IAnimalService {

    public Collection<BaseAnimal> all();

    BaseAnimal create(BaseAnimal animal);

    public BaseAnimal get(String id);


}
