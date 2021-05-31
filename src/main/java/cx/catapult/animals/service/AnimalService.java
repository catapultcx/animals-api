package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Insect;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AnimalService extends BaseService<BaseAnimal> {

    private CatsService catsService;
    private InsectsService insectsService;

    private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);

    public AnimalService(CatsService catsService, InsectsService insectsService) {
        this.catsService = catsService;
        this.insectsService = insectsService;
    }

    @PostConstruct
    public void initialize() {

    }

    public List<BaseAnimal> results(String keyword) {

        Collection<Cat> cats = catsService.all();
        Collection<Insect> insects = insectsService.all();

        return Stream.concat(
            cats.stream(),
            insects.stream())
            .filter(map -> map.getName().toLowerCase().contains(keyword.toLowerCase())
            || map.getDescription().toLowerCase().contains(keyword.toLowerCase())
            || map.getId().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());

    }
}
