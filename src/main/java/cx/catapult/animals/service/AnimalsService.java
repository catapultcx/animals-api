package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.AnimalImpl;
import cx.catapult.animals.domain.Group;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AnimalsService extends BaseService<Animal> {

    @PostConstruct
    public void initialize() {
        this.create(new AnimalImpl("Tom", "Friend of Jerry", Group.MAMMALS, "RED", "CAT"));
        this.create(new AnimalImpl("Jerry", "Not really a cat", Group.MAMMALS, "RED", "MICE"));
        this.create(new AnimalImpl("Nemo", "Clown Fish", Group.FISH, "RED", "Clown fish"));
        this.create(new AnimalImpl("Star", "Star Fish", Group.INVERTEBRATE, "BLUE", "Star fish"));
        this.create(new AnimalImpl("Mithu", "Green", Group.BIRD, "GREEN", "Indian Ring Neck"));
    }

}
