package cx.catapult.animals.service;

import cx.catapult.animals.domain.Arachnid;
import cx.catapult.animals.entity.ArachnidEntity;
import cx.catapult.animals.entity.ArachnidRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("db")
public class ArachnidDatabaseService implements DeleteableService<Arachnid> {
    private final ArachnidRepository arachnidRepository;

    private final ModelMapper modelMapper;

    public ArachnidDatabaseService(final ArachnidRepository arachnidRepository,
                                   final ModelMapper modelMapper) {
        this.arachnidRepository = arachnidRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<Arachnid> all() {
        return StreamSupport
                .stream(arachnidRepository.findAll()
                                     .spliterator(), false)
                .map(animal -> modelMapper.map(animal, Arachnid.class))
                .collect(Collectors.toList());
    }

    @Override
    public Arachnid create(final Arachnid animal) {
        ArachnidEntity arachnidEntity = modelMapper.map(animal, ArachnidEntity.class);
        arachnidEntity.setId(UUID.randomUUID()
                            .toString());
        ArachnidEntity saved = arachnidRepository.save(arachnidEntity);

        return modelMapper.map(saved, Arachnid.class);
    }

    @Override
    public Arachnid update(final String id,
                      final Arachnid animal) {
        ArachnidEntity arachnidEntity = modelMapper.map(animal, ArachnidEntity.class);
        arachnidEntity.setId(id);
        ArachnidEntity saved = arachnidRepository.save(arachnidEntity);

        return modelMapper.map(saved, Arachnid.class);
    }

    @Override
    public Arachnid get(final String id) {
        // Existing implementations return null so preserve behaviour
        ArachnidEntity found = arachnidRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Cannot find arachnid %s", id)));

        return found == null ? null : modelMapper.map(found, Arachnid.class);
    }

    @Override
    public Arachnid delete(final String id) {
        ArachnidEntity found = arachnidRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Cannot find arachnid %s", id)));
        arachnidRepository.delete(found);

        return modelMapper.map(found, Arachnid.class);
    }
}
