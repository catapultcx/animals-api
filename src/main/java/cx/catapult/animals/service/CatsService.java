package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CatsService extends BaseService<Cat> {

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

    public Cat delete(String id) {
        return this.items.remove(id);
    }

    public Cat update(String id, Cat updatedCat) {
        Cat existingCat = this.items.get(id);
        if (existingCat == null) {
            return null;
        }
        existingCat.setName(updatedCat.getName());
        existingCat.setDescription(updatedCat.getDescription());
        return existingCat;
    }
}
