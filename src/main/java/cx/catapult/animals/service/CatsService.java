package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CatsRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatsService {

  private final CatsRepository catsRepository;

  public Collection<Cat> all() {
    return catsRepository.getCats();
  }

  public Cat get(final String id) {
    return catsRepository.getCat(id);
  }

  public Cat create(final Cat cat) {
    return catsRepository.save(cat);
  }

  public Cat update(final String id, final Cat cat) {
    return catsRepository.update(id, cat);
  }

  public void delete(final String id) {
    catsRepository.delete(id);
  }

  @PostConstruct
  public void initialize() {
    final List<Cat> cats = new ArrayList<>();
    cats.add(new Cat("Tom", "Friend of Jerry"));
    cats.add(new Cat("Jerry", "Not really a cat"));
    cats.add(new Cat("Bili", "Furry cat"));
    cats.add(new Cat("Smelly", "Cat with friends"));
    cats.add(new Cat("Tiger", "Large cat"));
    cats.add(new Cat("Tigger", "Not a scary cat"));
    cats.add(new Cat("Garfield", "Lazy cat"));
    catsRepository.saveAll(cats);
  }
}
