package cx.catapult.animals.service;

import cx.catapult.animals.domain.Insect;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class InsectsService extends BaseService<Insect> {

    @PostConstruct
    public void initialize() {
        this.create(new Insect("Jimminy Cricket", "Friend of Pinnochio"));
    }

}
