package cx.catapult.animals.service;

import cx.catapult.animals.domain.Dog;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DogsServiceTest {

    DogsService service = new DogsService();

    @Test
    public void createShouldWork() throws Exception {
        Dog thisDog = new Dog();
        thisDog.setName("Spike");
        thisDog.setDescription("Mouse Dog");

        Dog actual = service.create(thisDog);

        assertThat(actual).isEqualTo(thisDog);
        assertThat(actual.getName()).isEqualTo(thisDog.getName());
        assertThat(actual.getDescription()).isEqualTo(thisDog.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisDog.getGroup());
    }
}
