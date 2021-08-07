package cx.catapult.animals.service;

import cx.catapult.animals.domain.Family;
import cx.catapult.animals.domain.Horse;
import cx.catapult.animals.repository.HorseRepository;
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
public class HorsesService extends BaseService<Horse> {

    @Autowired
    private HorseRepository horsesRepository;

    @Override
    public Collection<Horse> all() {
        Iterable<AnimalEntity> all = horsesRepository.findAll();
        Stream<AnimalEntity> stream = StreamSupport.stream(all.spliterator(), false)
                .filter(animalEntity -> animalEntity.getFamily().equals(Family.HORSE));

        return stream
                .map(this::mapAnimalEntityToHorse)
                .collect(Collectors.toList());
    }

    @Override
    public Horse create(Horse horse) {
        AnimalEntity animalEntity = mapHorseToAnimalEntity(horse);
        horsesRepository.save(animalEntity);
        return mapAnimalEntityToHorse(animalEntity);
    }

    @Override
    public Horse get(String id) {
        Optional<AnimalEntity> horseOptional = horsesRepository.findById(id);
        if(!horseOptional.isPresent())
            return null;
        AnimalEntity animalEntity = horseOptional.get();
        final Horse horse = mapAnimalEntityToHorse(animalEntity);
        return horse;
    }

    public boolean delete(String id){
        Optional<AnimalEntity> byId = horsesRepository.findById(id);
        if(byId.isPresent()) {
            horsesRepository.delete(byId.get());
            return true;
        }
        return false;
    }

    public void update(String id, Horse horse) {
        Optional<AnimalEntity> optionalAnimalEntity = horsesRepository.findById(id);
        if(optionalAnimalEntity.isPresent()){
            AnimalEntity animalEntity = optionalAnimalEntity.get();
            animalEntity.setDescription(horse.getDescription());
            animalEntity.setName(horse.getName());
            horsesRepository.save(animalEntity);
        }
    }

    private Horse mapAnimalEntityToHorse(AnimalEntity animalEntity) {
        Horse horse = new Horse(animalEntity.getName(), animalEntity.getDescription());
        horse.setId(animalEntity.getId());
        return horse;
    }

    private AnimalEntity mapHorseToAnimalEntity(Horse horse) {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(horse.getId());
        animalEntity.setName(horse.getName());
        animalEntity.setDescription(horse.getDescription());
        animalEntity.setGroup(horse.getGroup());
        animalEntity.setFamily(Family.HORSE);
        return animalEntity;
    }

    @PostConstruct
    public void initialize() {
        this.create(new Horse("Trigger", "The Smartest Horse"));
        this.create(new Horse("Marengo", "Famous horse of Napoleon Bonaparte"));
        this.create(new Horse("Bucephalus", "The famous horse owned by Alexander the Great"));
        this.create(new Horse("Seabiscuit", "One of the most famous racehorses of the 20th Century"));
        this.create(new Horse("Secretariat", "ESPN 100 Greatest Athletes of the Twentieth Century"));
    }

}
