package cx.catapult.animals.service;

import cx.catapult.animals.domain.Dog;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    public void allShouldWork() throws Exception {
        final Dog dog = new Dog("Spike", "Fluffy dog");

        service.create(dog);

        assertNotNull(dog.getId());
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        final Dog dog = new Dog("Spike", "Fluffy dog");

        service.create(dog);

        Dog actual = service.get(dog.getId());

        assertThat(actual).isEqualTo(dog);
        assertThat(actual.getName()).isEqualTo(dog.getName());
        assertThat(actual.getDescription()).isEqualTo(dog.getDescription());
        assertThat(actual.getGroup()).isEqualTo(dog.getGroup());
    }
}
