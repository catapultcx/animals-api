package cx.catapult.animals.service;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.repository.BirdRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BirdsService extends BaseService<Bird> {

    final BirdRepository birdRepository;

    public BirdsService(BirdRepository birdRepository) {
        this.birdRepository = birdRepository;
    }


    @Override
    CrudRepository<Bird, String> getRepository() {
        return this.birdRepository;
    }

    @PostConstruct
    public void initialize() {
        this.create(new Bird("Big", "Big Bird"));
        this.create(new Bird("Maltese", "Maltese Falcon"));
        this.create(new Bird("Dolly", "Dolly Bird"));
        this.create(new Bird("Polly", "Pretty Polly"));
    }
}
