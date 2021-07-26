package cx.catapult.animals.service;

import static cx.catapult.animals.domain.CatFactory.fromCat;
import static cx.catapult.animals.domain.CatFactory.fromCatEntity;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.CatFactory;
import cx.catapult.animals.entity.CatEntity;
import cx.catapult.animals.repository.CatRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class CatsService extends BaseService<Cat> {

    private final CatRepository catRepository;

    public CatsService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @PostConstruct
    public void initialize() {
        this.create(new Cat("Tom", "Friend of Jerry"));
        this.create(new Cat("Jerry", "Not really a cat"));
        this.create(new Cat("Bili", "Furry cat"));
        this.create(new Cat("Smelly", "Cat with friends"));
        this.create(new Cat("Tiger", "Large cat"));
        this.create(new Cat("Tigger", "Not a scary cat"));
        this.create(new Cat("Garfield", "Lazy cat"));
    }

    @Override
    public List<Cat> all() {
        return StreamSupport
            .stream(catRepository.findAll().spliterator(), false)
            .map(CatFactory::fromCatEntity)
            .collect(Collectors.toList());
    }

    @Override
    public Cat create(Cat cat) {
        CatEntity catEntity = fromCat(cat);
        return fromCatEntity(catRepository.save(catEntity));
    }

    @Override
    public Cat get(String id) {
        return fromCatEntity(catRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new)); //TODO custom exception and Controller Advice component
    }

    public Cat update(Cat catDetails, UUID catId) {
        CatEntity matching = catRepository.findById(catId).orElseThrow(RuntimeException::new); //TODO custom exception and Controller Advice component
        matching.setName(catDetails.getName());
        matching.setDescription(catDetails.getDescription());
        return fromCatEntity(catRepository.save(matching));
    }

}
