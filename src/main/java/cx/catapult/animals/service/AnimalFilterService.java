package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.entity.AnimalEntity;
import cx.catapult.animals.repository.AnimalRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnimalFilterService {
    private final AnimalRepository repository;

    public AnimalFilterService(AnimalRepository repository) {
        this.repository = repository;
    }

    public Collection<Animal> filter(
            Optional<List<String>> types,
            Optional<List<String>> names,
            Optional<List<String>> colors,
            Optional<List<String>> descriptions
    ) {
        return Animal.fromEntity(repository.findAll(
                (Specification<AnimalEntity>) (root, query, criteriaBuilder) -> criteriaBuilder.and(
                        get(criteriaBuilder,root, types, "type"),
                        get(criteriaBuilder,root, names, "name"),
                        get(criteriaBuilder,root, colors, "color"),
                        get(criteriaBuilder,root, descriptions, "description")
                ))
        );
    }

    private Predicate get(
            CriteriaBuilder cb,
            Root<AnimalEntity> root ,
            Optional<List<String>> ins ,
            String property
    ) {
        return ins.filter(
                strings -> !strings.isEmpty()
        ).map(strings -> root.get(property).in(strings)).orElse(cb.conjunction());
    }
}
