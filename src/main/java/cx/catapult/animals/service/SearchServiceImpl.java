package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.FilterCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private AnimalService<? extends BaseAnimal> animalService;

    @Override
    public List<? extends BaseAnimal> searchAnimals(FilterCriteria filterCriteria) {
        if (Objects.isNull(filterCriteria)) {
            return Collections.emptyList();
        }

        Collection<? extends BaseAnimal> all = animalService.all();
        Stream<? extends BaseAnimal> filterStream = null;
        if (nonNull(filterCriteria.getName())) {
            filterStream = all.stream().filter(n -> n.getName().equals(filterCriteria.getName()));
        }
        if (nonNull(filterCriteria.getDescription())) {
            filterStream = filterStream.filter(d -> filterCriteria.getDescription().equals(d.getDescription()));
        }
        if (nonNull(filterCriteria.getGroup())) {
            filterStream = filterStream.filter(g -> filterCriteria.getGroup().equals(g.getGroup()));
        }
        if (nonNull(filterCriteria.getColour())) {
            filterStream = filterStream.filter(x1 -> x1.getColours() != null).
                    filter(c -> Arrays.stream(c.getColours())
                            .anyMatch(x -> x.equals(filterCriteria.getColour())));
        }

        return  filterStream == null ? null : filterStream.collect(toList());
    }

    void setAnimalService(AnimalService<? extends BaseAnimal> animalService) {
        this.animalService = animalService;
    }
}
