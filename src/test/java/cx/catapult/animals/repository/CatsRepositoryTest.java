package cx.catapult.animals.repository;

import cx.catapult.animals.repository.entity.AnimalEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class CatsRepositoryTest {

    @Autowired
    private CatsRepository catsRepository;

    @Test
    void findAll() {
        catsRepository.save(new AnimalEntity());
        Iterable<AnimalEntity> all = catsRepository.findAll();
        List<AnimalEntity> result = new ArrayList<AnimalEntity>();
        all.forEach(result::add);
        assertThat(result.size()).isEqualTo(1);
    }
}
