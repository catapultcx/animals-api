package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Reptile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ReptilesService extends BaseService<Reptile>{

    @PostConstruct
    public void initialize() {
        this.create(new Reptile("Leo", "Leo lizard"));

    }
}
