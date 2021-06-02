package cx.catapult.animals.service;

import cx.catapult.animals.domain.Turtle;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TurtlesService extends BaseService<Turtle> {

    @PostConstruct
    public void initialize() {
        this.create(new Turtle("piggy", "Pig nosed"));
        this.create(new Turtle("african", "African Helmeted"));
        this.create(new Turtle("Bog", "Bog Turtle"));
        this.create(new Turtle("Reev", "Reeves Turtle"));
    }

}
