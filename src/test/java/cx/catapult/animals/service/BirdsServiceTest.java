package cx.catapult.animals.service;

import cx.catapult.animals.domain.Bird;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BirdsServiceTest {

    BirdsService service = new BirdsService();
    Bird bird = new Bird("Tom", "Bob bird");

    @Test
    public void createShouldWork()  {
        Bird thisBird = new Bird();
        thisBird.setName("Jerry");
        thisBird.setDescription("Mouse Bird");
        Bird actual = service.create(thisBird);
        assertThat(actual).isEqualTo(thisBird);
        assertThat(actual.getName()).isEqualTo(thisBird.getName());
        assertThat(actual.getDescription()).isEqualTo(thisBird.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisBird.getGroup());
    }

    @Test
    public void allShouldWork() {
        service.create(bird);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        service.create(bird);
        Bird actual = service.get(bird.getId());
        assertThat(actual).isEqualTo(bird);
        assertThat(actual.getName()).isEqualTo(bird.getName());
        assertThat(actual.getDescription()).isEqualTo(bird.getDescription());
        assertThat(actual.getGroup()).isEqualTo(bird.getGroup());
    }
    @Test
    public void deleteShouldWork() {
        service.create(bird);
        int size  = service.all().size();
        assertThat(size).isEqualTo(1);
        service.delete(bird.getId());
        int size2  = service.all().size();
        assertThat(size2).isEqualTo(0);
    }

}
