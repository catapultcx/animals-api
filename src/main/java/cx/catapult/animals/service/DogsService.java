package cx.catapult.animals.service;

import cx.catapult.animals.domain.Dog;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DogsService extends BaseService<Dog> {

    @PostConstruct
    public void initialize() {
        this.create(new Dog("Tom", "Friend of Jerry"));
        this.create(new Dog("Jerry", "Not really a dog"));
        this.create(new Dog("Bili", "Furry dog"));
        this.create(new Dog("Smelly", "Dog with friends"));
        this.create(new Dog("Tiger", "Large dog"));
        this.create(new Dog("Tigger", "Not a scary dog"));
        this.create(new Dog("Garfield", "Lazy dog"));
    }

}
