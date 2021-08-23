package cx.catapult.animals.service;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.repository.BirdsRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BirdsService {

  private final BirdsRepository birdsRepository;

  @PostConstruct
  public void initialize() {

    final List<Bird> birds = new ArrayList<>();
    birds.add(new Bird("Barn owl", "With bright white feathers and a distinctive heart-shaped face"));
    birds.add(new Bird("Black grouse", "An iconic bird which is becoming an increasingly rare sight"));
    birds.add(new Bird("Blackbird", "Garden stalwarts. Famous songsters. Familiar friends."));
    birds.add(new Bird("Blackcap",
        "Named after the male’s signature black cap, this little bird arrives in the UK each spring, bringing with it the sweet sound of its beautiful song"));
    birds.add(new Bird("Blue tit", "The blue tit might be famed for its bright,"));
    birds.add(new Bird("Brambling", "Winter visitors with a taste for nuts. "));
    birds.add(new Bird("Bullfinch", "Shy but striking. Bullfinches bring a brilliant burst of colour to the UK’s woods"));
    birdsRepository.saveAll(birds);
  }

  public Collection<Bird> all() {
    return birdsRepository.getBirds();
  }

  public Bird get(final String id) {
    return birdsRepository.getBird(id);
  }

  public Bird create(final Bird bird) {
    return birdsRepository.save(bird);
  }

  public Bird update(final String id, final Bird bird) {
    return birdsRepository.update(id, bird);
  }

  public void delete(final String id) {
    birdsRepository.delete(id);
  }
}
