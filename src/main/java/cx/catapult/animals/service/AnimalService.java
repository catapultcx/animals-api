package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Type;
import cx.catapult.animals.web.AnimalFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AnimalService extends BaseService<BaseAnimal> {

    @PostConstruct
    public void initialize() {
        this.create(new BaseAnimal("Ganga", "a happy cow", "white", Type.MAMMALS));
        this.create(new BaseAnimal("Cuckoo", "a joyous bird", "blue", Type.BIRD));
        this.create(new BaseAnimal("Gecko", "a Reptile", "green", Type.REPTILES));
        this.create(new BaseAnimal("Jelly", "a sticky frog", "grey", Type.AMPHIBIAN));
        this.create(new BaseAnimal("Cliona", "an insect", "brown", Type.INVERTEBRATE));
        this.create(new BaseAnimal("Sagar", "a beautiful fish", "yellow", Type.FISH));
        this.create(new BaseAnimal("Yamuna", "a calf", "brown", Type.MAMMALS));
        this.create(new BaseAnimal("Malli", "an good insect", "black", Type.INVERTEBRATE));
    }

    @Override
    public Collection<BaseAnimal> search(AnimalFilter filter) {
        List<Predicate<BaseAnimal>> predicates = new ArrayList<>();

        if(StringUtils.isNotBlank(filter.getName())) {
            predicates.add(byAnimalName(filter));
        }
        if(StringUtils.isNotBlank(filter.getColor())) {
            predicates.add(byColor(filter));
        }
        if(StringUtils.isNotBlank(filter.getDescription())) {
            predicates.add(byDescription(filter));
        }
        if(StringUtils.isNotBlank(filter.getType())) {
            predicates.add(byType(filter));
        }

        return getAnimalStore().values().stream()
                .filter(predicates.stream().reduce(x -> true, Predicate::and))
                .collect(Collectors.toList());
    }

    private  Predicate<BaseAnimal> byAnimalName(AnimalFilter filter) {
        return animal -> animal.getName().equalsIgnoreCase(filter.getName());
    }

    private  Predicate<BaseAnimal> byColor(AnimalFilter filter) {
        return animal -> animal.getColor().equalsIgnoreCase(filter.getColor());
    }

    private  Predicate<BaseAnimal> byDescription(AnimalFilter filter) {
        return animal -> animal.getDescription().equalsIgnoreCase(filter.getDescription());
    }

    private  Predicate<BaseAnimal> byType(AnimalFilter filter) {
        return animal -> animal.getType().name().equalsIgnoreCase(filter.getType());
    }

    public void deleteAll() {
        getAnimalStore().clear();
    }
}
