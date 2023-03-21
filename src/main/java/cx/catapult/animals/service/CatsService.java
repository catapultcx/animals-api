package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.stream.Collectors;

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

    @Override
    public Collection<Cat> filter(String search) {
        return this.all()
                .stream()
                .filter(
                        cat -> cat.getName().toLowerCase().contains(search.toLowerCase()) ||
                                cat.getDescription().toLowerCase().contains(search.toLowerCase())
                ).collect(Collectors.toSet());
    }

}
