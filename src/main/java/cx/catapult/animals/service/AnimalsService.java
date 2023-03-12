package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AnimalsService extends BaseService<BaseAnimal> {
    @PostConstruct
    public void initialize() {
        this.create(new BaseAnimal("Tom", "Tree frog", Group.AMPHIBIAN, "Green", "Frog"));
        this.create(new BaseAnimal("Mary", "A bird", Group.BIRD, "Brown", "Kestrel"));
        this.create(new BaseAnimal("Zeus", "Labrador x staffie", Group.MAMMALS, "Black", "Dog"));
        this.create(new BaseAnimal("Josie", "Lurcher", Group.MAMMALS, "Grey", "Dog"));
        this.create(new BaseAnimal("Romeo", "Welsh cob", Group.MAMMALS, "Brown", "Horse"));
        this.create(new BaseAnimal("Kate", "House spider", Group.INVERTEBRATE, "Red", "Spider"));
        this.create(new BaseAnimal("Jim", "Cod", Group.FISH, "White", "Fish"));
        this.create(new BaseAnimal("Kim", "Reticulated python ", Group.FISH, "Brown", "Snake"));
    }
}
