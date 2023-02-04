package cx.catapult.animals.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
@Service
public class AnimalService implements IAnimalService {

    private HashMap<String, BaseAnimal> items = new HashMap<>();

    @PostConstruct
    public void initialize() {
        this.create(new BaseAnimal("Tom", "Cat", "Grey", "Friend of Jerry", Group.MAMMALS));
        this.create(new BaseAnimal("Jerry", "Cat", "Grey", "Not really a cat", Group.MAMMALS));
        this.create(new BaseAnimal("Bili", "Cat", "Grey", "Furry cat", Group.MAMMALS));
        this.create(new BaseAnimal("Smelly", "Cat", "Grey", "Cat with friends", Group.MAMMALS));
        this.create(new BaseAnimal("Tiger", "Cat", "Grey", "Large cat", Group.MAMMALS));
        this.create(new BaseAnimal("Tigger", "Cat", "Grey", "Not a scary cat", Group.MAMMALS));
        this.create(new BaseAnimal("Garfield", "Cat", "Grey", "Lazy cat", Group.MAMMALS));
    }

    @Override
    public Collection<BaseAnimal> all() {
        return items.values();
    }

    @Override
    public BaseAnimal create(BaseAnimal animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        items.put(id, animal);
        return animal;
    }

    @Override
    public BaseAnimal get(String id) {
        return items.get(id);
    }
}
