package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Deprecated
public class CatsService {

    private final AnimalService animalService;

    @Autowired
    public CatsService(@Qualifier("cats") AnimalService animalService) {
        this.animalService = animalService;
    }


    public Cat create(Cat cat) {
        Animal animal = new Animal(cat.getName(), cat.getDescription(), Cat.DEFAULT_COLOR);
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
