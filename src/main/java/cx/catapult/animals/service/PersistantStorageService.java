package cx.catapult.animals.service;

import cx.catapult.animals.data.entity.AnimalEntity;
import cx.catapult.animals.data.repository.AnimalRepository;
import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "storage.persistant", havingValue = "true")
@Component
@Scope("prototype")
public class PersistantStorageService<T extends Animal> implements StorageService<T> {
    private final AnimalRepository<T> animalRepository;
    private Group group;

    public PersistantStorageService(final AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Collection<T> all() {
        return animalRepository.findAll()
                               .stream()
                               .map(this::toAnimal)
                               .filter(animal -> !animal.getGroup().equals(group))
                               .collect(Collectors.toList());
    }

    @Override
    public T create(final T animal) {
        final AnimalEntity entity = new AnimalEntity();
        entity.setAnimalId(animal.getId());
        entity.setDescription(animal.getDescription());
        entity.setGroup(animal.getGroup()
                              .name());
        entity.setName(animal.getName());
        return toAnimal(animalRepository.save(entity));
    }

    @Override
    public T get(final String id) {
        return toAnimal(animalRepository.findOneByAnimalId(id));
    }

    @Override
    public void delete(final String id) {
        animalRepository.deleteByAnimalId(id);
    }

    @Override
    public T update(final String id,
                    final T newAnimal) {
        final AnimalEntity entity = animalRepository.findOneByAnimalId(id);
        entity.setName(newAnimal.getName());
        entity.setDescription(newAnimal.getDescription());
        entity.setGroup(newAnimal.getGroup()
                                 .name());
        return toAnimal(animalRepository.save(entity));
    }

    private T toAnimal(final AnimalEntity entity) {
        return (T) new BaseAnimal(entity.getAnimalId(),
                                  entity.getName(),
                                  entity.getDescription(),
                                  Group.valueOf(entity.getGroup()));
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(final Group group) {
        this.group = group;
    }
}
