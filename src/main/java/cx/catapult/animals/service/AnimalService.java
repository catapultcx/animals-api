package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AnimalService extends BaseService<BaseAnimal> {

    @PostConstruct
    public void initialize() {
        this.create(new Cat("Tom", "Friend of Jerry", "Orange"));
        this.create(new Cat("Jerry", "Not really a cat", "Orange"));
        this.create(new Cat("Bili", "Furry cat", "Orange"));
        this.create(new Cat("Smelly", "Cat with friends", "Orange"));
        this.create(new Cat("Tiger", "Large cat", "Orange"));
        this.create(new Cat("Tigger", "Not a scary cat", "Orange"));
        this.create(new Cat("Garfield", "Lazy cat", "Orange"));
    }

}
