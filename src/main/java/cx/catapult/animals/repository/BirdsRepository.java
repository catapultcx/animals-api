package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.entity.BirdEntity;
import cx.catapult.animals.exception.AnimalNotFoundException;
import cx.catapult.animals.mapper.BirdsMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BirdsRepository {

  private final BirdsCrudRepository birdsCrudRepository;
  private final BirdsMapper birdsMapper;

  public List<Bird> getBirds() {
    return birdsMapper.getBirds(birdsCrudRepository.findAll());
  }

  public Bird save(final Bird bird) {
    return birdsMapper.getBird(birdsCrudRepository.save(birdsMapper.getBirdEntity(bird)));
  }

  public Bird getBird(final String id) {
    final Optional<BirdEntity> birdEntity = birdsCrudRepository.findById(id);
    if (birdEntity.isPresent()) {
      return birdsMapper.getBird(birdEntity.get());
    } else {
      throw new AnimalNotFoundException(id);
    }
  }

  public Bird update(final String id, final Bird bird) {
    final Optional<BirdEntity> birdEntity = birdsCrudRepository.findById(id);
    if (birdEntity.isPresent()) {
      final BirdEntity entity = birdEntity.get();
      entity.setName(bird.getName());
      entity.setDescription(bird.getDescription());
      final BirdEntity newBirdEntity = birdsCrudRepository.save(entity);
      return birdsMapper.getBird(newBirdEntity);
    } else {
      throw new AnimalNotFoundException(id);
    }
  }

  public void delete(final String id) {
    final Optional<BirdEntity> birdEntity = birdsCrudRepository.findById(id);
    if (birdEntity.isPresent()) {
      birdsCrudRepository.delete(birdEntity.get());
    } else {
      throw new AnimalNotFoundException(id);
    }
  }

  public void saveAll(final List<Bird> birds) {
    birdsCrudRepository.saveAll(birdsMapper.getBirdEntities(birds));
  }
}
