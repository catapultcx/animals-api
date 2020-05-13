package cx.catapult.animals.service;

import cx.catapult.animals.domain.Bird;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BirdsServiceTest {

    BirdsService service = new BirdsService();
    Bird bird = new Bird("Tom", "Bob bird");

    @Test
    public void createShouldWork() throws Exception {
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
    public void allShouldWork() throws Exception {
        service.create(bird);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(bird);
        Bird actual = service.get(bird.getId());
        assertThat(actual).isEqualTo(bird);
        assertThat(actual.getName()).isEqualTo(bird.getName());
        assertThat(actual.getDescription()).isEqualTo(bird.getDescription());
        assertThat(actual.getGroup()).isEqualTo(bird.getGroup());
    }
}
