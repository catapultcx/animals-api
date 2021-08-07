package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Family;
import cx.catapult.animals.repository.CatsRepository;
import cx.catapult.animals.repository.entity.AnimalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class CatsService extends BaseService<Cat> {

    @Autowired
    private CatsRepository catsRepository;

    @Override
    public Collection<Cat> all() {
        Iterable<AnimalEntity> all = catsRepository.findAll();
        Stream<AnimalEntity> stream = StreamSupport.stream(all.spliterator(), false)
                .filter(animalEntity -> animalEntity.getFamily().equals(Family.CAT));

        return stream
                .map(this::mapCatEntityToCat)
                .collect(Collectors.toList());
    }

    @Override
    public Cat create(Cat cat) {
        AnimalEntity animalEntity = mapCatToCatEntity(cat);
        catsRepository.save(animalEntity);
        return mapCatEntityToCat(animalEntity);
    }

    @Override
    public Cat get(String id) {
        Optional<AnimalEntity> catOptional = catsRepository.findById(id);
        if(!catOptional.isPresent())
            return null;
        AnimalEntity animalEntity = catOptional.get();
        final Cat cat = mapCatEntityToCat(animalEntity);
        return cat;
    }

    public boolean delete(String id){
        Optional<AnimalEntity> byId = catsRepository.findById(id);
        if(byId.isPresent()) {
            catsRepository.delete(byId.get());
            return true;
        }
        return false;
    }

    public void update(String id, Cat cat) {
        Optional<AnimalEntity> optionalAnimalEntity = catsRepository.findById(id);
        if(optionalAnimalEntity.isPresent()){
            AnimalEntity animalEntity = optionalAnimalEntity.get();
            animalEntity.setDescription(cat.getDescription());
            animalEntity.setName(cat.getName());
            catsRepository.save(animalEntity);
        }
    }

    private Cat mapCatEntityToCat(AnimalEntity animalEntity){
        Cat cat = new Cat(animalEntity.getName(), animalEntity.getDescription());
        cat.setId(animalEntity.getId());
        return cat;
    }

    private AnimalEntity mapCatToCatEntity(Cat cat){
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(cat.getId());
        animalEntity.setName(cat.getName());
        animalEntity.setDescription(cat.getDescription());
        animalEntity.setGroup(cat.getGroup());
        animalEntity.setFamily(Family.CAT);
        return animalEntity;
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
