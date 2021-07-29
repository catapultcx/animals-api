package cx.catapult.animals.service;

import cx.catapult.animals.domain.Horse;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class HorseService extends BaseService<Horse> {

    @PostConstruct
    public void initialize() {
        this.create(new Horse("Kincsem", "Won 54 races"));
        this.create(new Horse("Black Caviar", "Won 25 races"));
        this.create(new Horse("Peppers Pride", "Won 19 races"));
        this.create(new Horse("Eclipse", "Won 18 races"));
        this.create(new Horse("Karayel", "Won 18 races"));
        this.create(new Horse("Ormonde", "Won 16 races"));
        this.create(new Horse("Prestige", "Won 16 races"));
    }
}
