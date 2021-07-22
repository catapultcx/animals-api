package cx.catapult.animals.service;

import cx.catapult.animals.domain.Horse;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class HorseService extends BaseService<Horse> {

    @PostConstruct
    public void initialize() {
        this.create(new Horse("Spirit", "Stallion of Cimarron"));
        this.create(new Horse("Stallion", "The Black Stallion"));
        this.create(new Horse("Horse", "War Horse"));
        this.create(new Horse("Beauty", "Black Beauty"));
    }

}
