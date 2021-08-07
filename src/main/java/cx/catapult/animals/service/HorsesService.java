package cx.catapult.animals.service;

import cx.catapult.animals.domain.Horse;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class HorsesService extends BaseService<Horse> {

    @PostConstruct
    public void initialize() {
        this.create(new Horse("Trigger", "The Smartest Horse"));
        this.create(new Horse("Marengo", "Famous horse of Napoleon Bonaparte"));
        this.create(new Horse("Bucephalus", "The famous horse owned by Alexander the Great"));
        this.create(new Horse("Seabiscuit", "One of the most famous racehorses of the 20th Century"));
        this.create(new Horse("Secretariat", "ESPN 100 Greatest Athletes of the Twentieth Century"));
    }

}
