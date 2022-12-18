package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AnimalsService extends BaseService<BaseAnimal> {

    private final CatsService legacyCatsService;

    @Autowired
    public AnimalsService(CatsService legacyCatsService) {
        this.legacyCatsService = legacyCatsService;
    }

    @PostConstruct
    public void initialize() {
        this.addAll(legacyCatsService.all().stream().map(asBaseAnimal()).collect(Collectors.toSet()));
    }

    private Function<Cat, BaseAnimal> asBaseAnimal() {
        return cat -> new BaseAnimal(
                cat.getName(),
                cat.getDescription(),
                cat.getGroup(),
                cat.getType(),
                cat.getColour());
    }

}
