package cx.catapult.animals.service;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.repo.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrustaceanService {

    private AnimalRepository animalRepository;

    @Autowired
    private CrustaceanService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public Crustacean create(Crustacean crustacean) {
        animalRepository.save(crustacean);
        return crustacean;
    }
}
