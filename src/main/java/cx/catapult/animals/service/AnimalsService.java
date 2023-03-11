package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AnimalsService extends BaseService<BaseAnimal> {
    @PostConstruct
    public void initialize() {
        this.create(new BaseAnimal("Tom", "Tree frog", Group.AMPHIBIAN, "Frog"));
        this.create(new BaseAnimal("Mary", "A bird", Group.BIRD, "Kestrel"));
        this.create(new BaseAnimal("Zeus", "Labrador x staffie", Group.MAMMALS, "Dog"));
        this.create(new BaseAnimal("Josie", "Lurcher", Group.MAMMALS, "Dog"));
        this.create(new BaseAnimal("Romeo", "Welsh cob", Group.MAMMALS, "Horse"));
        this.create(new BaseAnimal("Kate", "House spider", Group.INVERTEBRATE, "Spider"));
        this.create(new BaseAnimal("Jim", "Cod", Group.FISH, "Fish"));
        this.create(new BaseAnimal("Kim", "Reticulated python ", Group.FISH, "Snake"));
    }
}
