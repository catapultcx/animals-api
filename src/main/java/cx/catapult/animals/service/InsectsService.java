package cx.catapult.animals.service;

import cx.catapult.animals.domain.Insect;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InsectsService extends BaseService<Insect> {

    private static final Logger logger = LoggerFactory.getLogger(InsectsService.class);

    @PostConstruct
    public void initialize() {
        this.create(new Insect("Jimminy Cricket", "Friend of Pinnochio"));
    }

    public void delete(String id) {
        Insect insect = super.items.remove(id);
        logger.info("Deleted id {} containing the insect {}", id, insect);
    }

}
