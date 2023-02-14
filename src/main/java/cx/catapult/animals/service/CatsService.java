package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.exceptions.UnsupportedAnimalTypeException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Service
@Deprecated
public class CatsService {
    private final AnimalService animalService =
            new AnimalService(Animal.AnimalType.CAT);

    public CatsService() {
    }

    @PostConstruct
    public void initialize() {
        try {
            this.animalService.create(Animal.from("cat", "Tom", "Friend of Jerry"));
            this.animalService.create(Animal.from("cat", "Jerry", "Not really a cat"));
            this.animalService.create(Animal.from("cat", "Bili", "Furry cat"));
            this.animalService.create(Animal.from("cat", "Smelly", "Cat with friends"));
            this.animalService.create(Animal.from("cat", "Tiger", "Large cat"));
            this.animalService.create(Animal.from("cat", "Tigger", "Not a scary cat"));
            this.animalService.create(Animal.from("cat", "Garfield", "Lazy cat"));
        } catch (UnsupportedAnimalTypeException e) {
            throw new RuntimeException(e);
        }

    }

    public Cat create(Cat cat) throws UnsupportedAnimalTypeException {
        Animal animal = Animal.from("cat", cat.getName(), cat.getDescription());
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
