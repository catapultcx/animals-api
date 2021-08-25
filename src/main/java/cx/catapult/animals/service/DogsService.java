package cx.catapult.animals.service;

import cx.catapult.animals.domain.Dog;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DogsService extends BaseService<Dog> {

    @PostConstruct
    public void initialize() {
        this.create(new Dog("Dante", "Coco"));
        this.create(new Dog("Scooby", "Doo"));
    }

}
