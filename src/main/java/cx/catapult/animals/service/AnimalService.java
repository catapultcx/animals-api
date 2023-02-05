package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import cx.catapult.animals.service.predicates.ColorMatchPredicate;
import cx.catapult.animals.service.predicates.DescriptionMatchPredicate;
import cx.catapult.animals.service.predicates.NameMatchPredicate;
import cx.catapult.animals.service.predicates.TypeMatchPredicate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnimalService implements IAnimalService {

    private HashMap<String, BaseAnimal> items = new HashMap<>();

    @PostConstruct
    public void initialize() {
        this.create(new BaseAnimal("Tom", "Cat", "Grey", "Friend of Jerry", Group.MAMMALS));
        this.create(new BaseAnimal("Jerry", "Cat", "Grey", "Not really a cat", Group.MAMMALS));
        this.create(new BaseAnimal("Bili", "Cat", "Grey", "Furry cat", Group.MAMMALS));
        this.create(new BaseAnimal("Smelly", "Cat", "Grey", "Cat with friends", Group.MAMMALS));
        this.create(new BaseAnimal("Tiger", "Cat", "Grey", "Large cat", Group.MAMMALS));
        this.create(new BaseAnimal("Tigger", "Cat", "Grey", "Not a scary cat", Group.MAMMALS));
        this.create(new BaseAnimal("Garfield", "Cat", "Grey", "Lazy cat", Group.MAMMALS));
    }

    @Override
    public Collection<BaseAnimal> all() {
        return items.values();
    }

    @Override
    public BaseAnimal create(BaseAnimal animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        items.put(id, animal);
        return animal;
    }

    @Override
    public BaseAnimal get(String id) {
        return items.get(id);
    }

    @Override
    public void update(BaseAnimal animal) {
        BaseAnimal baseAnimal = items.get(animal.getId());
        baseAnimal.setName(animal.getName());
        baseAnimal.setType(animal.getType());
        baseAnimal.setColor(animal.getColor());
        baseAnimal.setDescription(animal.getDescription());

        items.put(animal.getId(), baseAnimal);
    }

    @Override
    public void delete(String id) {
        items.remove(id);
    }
    
    public List<BaseAnimal> filter(String name, String color, String type, String description) {
        ColorMatchPredicate colorMatchPredicate = new ColorMatchPredicate(color);
        NameMatchPredicate nameMatchPredicate = new NameMatchPredicate(name);
        TypeMatchPredicate typeMatchPredicate = new TypeMatchPredicate(type);
        DescriptionMatchPredicate descriptionMatchPredicate = new DescriptionMatchPredicate(description);
        return this.items
                .entrySet().stream()
                .filter(nameMatchPredicate
                        .and(colorMatchPredicate)
                        .and(typeMatchPredicate)
                        .and(descriptionMatchPredicate))
                .map(Map.Entry::getValue).collect(Collectors.toList());

    }
}
