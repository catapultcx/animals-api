package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Type;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AnimalService extends BaseService<BaseAnimal> {

    @PostConstruct
    public void initialize() {
        this.create(new BaseAnimal("Ganga", "a happy cow", "white", Type.MAMMALS));
        this.create(new BaseAnimal("Cuckoo", "a joyous bird", "blue", Type.BIRD));
        this.create(new BaseAnimal("Gecko", "a Reptile", "green", Type.REPTILES));
        this.create(new BaseAnimal("Jelly", "a sticky frog", "grey", Type.AMPHIBIAN));
        this.create(new BaseAnimal("Cliona", "an insect", "brown", Type.INVERTEBRATE));
        this.create(new BaseAnimal("Sagar", "a beautiful fish", "yellow", Type.FISH));
        this.create(new BaseAnimal("Yamuna", "a calf", "brown", Type.MAMMALS));
        this.create(new BaseAnimal("Malli", "an good insect", "black", Type.INVERTEBRATE));
    }

    public void deleteAll() {
        getAnimalStore().clear();
    }
}
