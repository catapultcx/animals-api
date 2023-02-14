package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Service
@Deprecated
public class CatsService {

    private final AnimalService animalService;

    @Autowired
    public CatsService(@Qualifier("cats") AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostConstruct
    public void initialize() {
        this.animalService.create(new Animal("Tom", "Friend of Jerry"));
        this.animalService.create(new Animal("Jerry", "Not really a cat"));
        this.animalService.create(new Animal("Bili", "Furry cat"));
        this.animalService.create(new Animal("Smelly", "Cat with friends"));
        this.animalService.create(new Animal("Tiger", "Large cat"));
        this.animalService.create(new Animal("Tigger", "Not a scary cat"));
        this.animalService.create(new Animal("Garfield", "Lazy cat"));
    }

    public Cat create(Cat cat) {
        Animal animal = new Animal(cat.getName(), cat.getDescription());
        cat.setAnimal(this.animalService.create(animal));
        return cat;
    }

    public Collection<Cat> all() {
        Collection<Animal> all = this.animalService.all();
        return all != null ? Cat.fromAnimals(all) : null;
    }

    public Cat get(String id) {
        Animal animal = this.animalService.get(id);
        return animal != null ? Cat.fromAnimal(animal) : null;
    }
}
