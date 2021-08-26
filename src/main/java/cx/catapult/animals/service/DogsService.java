package cx.catapult.animals.service;

import cx.catapult.animals.domain.Dog;
import cx.catapult.animals.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DogsService extends BaseService<Dog> {

    public DogsService(AnimalRepository animalRepository) {
        super(animalRepository);
    }

    @PostConstruct
    public void initialize() {
        this.create(new Dog("Dante", "Coco"));
        this.create(new Dog("Scooby", "Doo"));
    }

}
