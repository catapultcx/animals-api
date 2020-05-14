package cx.catapult.animals.service;

import cx.catapult.animals.domain.Bird;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BirdsServiceInMem extends BaseServiceInMem<Bird> {

    @PostConstruct
    public void initialize() {
        this.create(new Bird("Tweety", "Friend of Donald"));
        this.create(new Bird("Donald", "Not really a bird"));
        this.create(new Bird("Pigeon", "Furry bird"));
        this.create(new Bird("Road runner", "Bird with friends"));
        this.create(new Bird("Woodstock", "Large bird"));
        this.create(new Bird("Zazu", "Not a scary bird"));
        this.create(new Bird("Richard", "Lazy bird"));
    }

}
