package cx.catapult.animals.service;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import cx.catapult.animals.domain.Animal;

@Service
public class AnimalService extends BaseService<Animal> {

    @PostConstruct
    public void initialize() {
        this.create(new Animal("Tom", "Friend of Jerry", "red", "amphibian"));
        this.create(new Animal("Jerry", "Not really a cat", "blue", "bird"));
        this.create(new Animal("Bili", "Furry cat", "pink", "fish"));
        this.create(new Animal("Smelly", "Cat with friends", "grey", "invertebrate"));
        this.create(new Animal("Tiger", "Large cat", "black", "mammals"));
        this.create(new Animal("Tigger", "Not a scary cat", "white", "reptiles"));
        this.create(new Animal("Garfield", "Lazy cat", "green", "reptiles"));
    }
}
