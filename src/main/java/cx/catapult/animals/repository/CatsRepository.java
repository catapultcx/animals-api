package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.entity.CatEntity;
import cx.catapult.animals.exception.AnimalNotFoundException;
import cx.catapult.animals.mapper.CatsMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CatsRepository {

  private final CatsCrudRepository catsCrudRepository;
  private final CatsMapper catsMapper;

  public List<Cat> getCats() {
    return catsMapper.getCats(catsCrudRepository.findAll());
  }

  public Cat save(final Cat cat) {
    return catsMapper.getCat(catsCrudRepository.save(catsMapper.getCatEntity(cat)));
  }

  public Cat getCat(final String id) {
    final Optional<CatEntity> catEntity = catsCrudRepository.findById(id);
    if (catEntity.isPresent()) {
      return catsMapper.getCat(catEntity.get());
    } else {
      throw new AnimalNotFoundException(id);
    }
  }

  public Cat update(final String id, final Cat cat) {
    final Optional<CatEntity> catEntity = catsCrudRepository.findById(id);
    if (catEntity.isPresent()) {
      final CatEntity entity = catEntity.get();
      entity.setName(cat.getName());
      entity.setDescription(cat.getDescription());
      final CatEntity newCatEntity = catsCrudRepository.save(entity);
      return catsMapper.getCat(newCatEntity);
    } else {
      throw new AnimalNotFoundException(id);
    }
  }

  public void delete(final String id) {
    final Optional<CatEntity> catEntity = catsCrudRepository.findById(id);
    if (catEntity.isPresent()) {
      catsCrudRepository.delete(catEntity.get());
    } else {
      throw new AnimalNotFoundException(id);
    }
  }

  public void saveAll(final List<Cat> cats) {
    catsCrudRepository.saveAll(catsMapper.getCatEntities(cats));
  }
}
