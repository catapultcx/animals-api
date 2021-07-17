package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.entity.CatEntity;
import cx.catapult.animals.entity.CatRepository;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("db")
public class CatsDatabaseService implements Service<Cat> {
    private final CatRepository catRepository;

    private final ModelMapper modelMapper;

    public CatsDatabaseService(final CatRepository catRepository,
                               final ModelMapper modelMapper) {
        this.catRepository = catRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<Cat> all() {
        return StreamSupport
                .stream(catRepository.findAll()
                                     .spliterator(), false)
                .map(animal -> modelMapper.map(animal, Cat.class))
                .collect(Collectors.toList());
    }

    @Override
    public Cat create(final Cat animal) {
        CatEntity catEntity = modelMapper.map(animal, CatEntity.class);
        catEntity.setId(UUID.randomUUID()
                            .toString());
        CatEntity saved = catRepository.save(catEntity);

        return modelMapper.map(saved, Cat.class);
    }

    @Override
    public Cat update(final String id,
                      final Cat animal) {
        CatEntity catEntity = modelMapper.map(animal, CatEntity.class);
        catEntity.setId(id);
        CatEntity saved = catRepository.save(catEntity);

        return modelMapper.map(saved, Cat.class);
    }

    @Override
    public Cat get(final String id) {
        // Existing implementations return null so preserve behaviour
        CatEntity found = catRepository.findById(id)
                                       .orElse(null);

        return found == null ? null : modelMapper.map(found, Cat.class);
    }
}
