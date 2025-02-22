package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Cat;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FakeCatsRepository implements CatsRepository {
    private final Map<String, Cat> entities = new ConcurrentHashMap<>();
    
    private static final List<Cat> INITIAL_CATS = List.of(
        new Cat("Tom", "Friend of Jerry"),
        new Cat("Jerry", "Not really a cat"),
        new Cat("Bili", "Furry cat"),
        new Cat("Smelly", "Cat with friends"),
        new Cat("Tiger", "Large cat"),
        new Cat("Tigger", "Not a scary cat"),
        new Cat("Garfield", "Lazy cat")
    );

    @Override
    public Cat create(Cat entity) {
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Cat get(String id) {
        return entities.get(id);
    }

    @Override
    public List<Cat> all() {
        return new ArrayList<>(entities.values());
    }

    @PostConstruct
    public void initialize() {
        INITIAL_CATS.forEach(this::create);
    }
} 