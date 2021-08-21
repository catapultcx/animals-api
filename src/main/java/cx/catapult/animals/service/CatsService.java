package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CatRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CatsService extends BaseService<Cat> {

    final CatRepository catRepository;

    public CatsService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    CrudRepository<Cat, String> getRepository() {
        return this.catRepository;
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

}
