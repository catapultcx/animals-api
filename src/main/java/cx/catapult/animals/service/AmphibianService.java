package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAmphibian;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class AmphibianService extends BaseService<BaseAmphibian> {
    @PostConstruct
    public void initialize() {
        this.create(new BaseAmphibian("Lizzie", "The friendly sand gecko"));
    }

}
