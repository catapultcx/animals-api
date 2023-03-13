package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AnimalsService extends BaseService<BaseAnimal> {
    @PostConstruct
    public void initialize() {
        this.create(new BaseAnimal("123", "Tom", "Tree frog", Group.AMPHIBIAN, "Frog", "Green"));
        this.create(new BaseAnimal("Mary", "A bird", Group.BIRD,  "Kestrel", "Brown"));
        this.create(new BaseAnimal("Zeus", "Labrador x staffie", Group.MAMMALS, "Dog", "Black"));
        this.create(new BaseAnimal("Josie", "Lurcher", Group.MAMMALS, "Dog", "Grey"));
        this.create(new BaseAnimal("Romeo", "Welsh cob", Group.MAMMALS, "Horse", "Brown"));
        this.create(new BaseAnimal("Kate", "House spider", Group.INVERTEBRATE, "Spider", "Red"));
        this.create(new BaseAnimal("Jim", "Cod", Group.FISH, "Fish", "White"));
        this.create(new BaseAnimal("Kim", "Reticulated python ", Group.REPTILES, "Snake", "Brown"));
    }
}
