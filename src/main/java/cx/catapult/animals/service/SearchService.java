package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.FilterCriteria;

import java.util.List;

public interface SearchService {
    List<? extends BaseAnimal> searchAnimals(FilterCriteria filterCriteria);
}
