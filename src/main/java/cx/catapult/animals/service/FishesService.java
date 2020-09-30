package cx.catapult.animals.service;

import cx.catapult.animals.domain.Fish;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FishesService extends BaseService<Fish> {

    @PostConstruct
    public void initialize() {
        this.create(new Fish("Nemo", "Friend of Dory"));
        this.create(new Fish("Dory", "I dont remember"));
        this.create(new Fish("Marlin", "Father of Nemo"));
        this.create(new Fish("Gill", "A nice fish afterall"));
        this.create(new Fish("Bloat", "Puff fish"));
        this.create(new Fish("Crush", "His a turtle"));
        this.create(new Fish("Bruce", "Evil shark"));
    }

}
