package cx.catapult.animals.service;

import cx.catapult.animals.domain.Dog;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DogsServiceTest {

    DogsService service = new DogsService();
    Dog dog = new Dog("Tom", "Bob dog");

    @Test
    public void createShouldWork() throws Exception {
        Dog thisDog = new Dog();
        thisDog.setName("Jerry");
        thisDog.setDescription("Mouse Dog");
        Dog actual = service.create(thisDog);
        assertThat(actual).isEqualTo(thisDog);
        assertThat(actual.getName()).isEqualTo(thisDog.getName());
        assertThat(actual.getDescription()).isEqualTo(thisDog.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisDog.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(dog);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(dog);
        Dog actual = service.get(dog.getId());
        assertThat(actual).isEqualTo(dog);
        assertThat(actual.getName()).isEqualTo(dog.getName());
        assertThat(actual.getDescription()).isEqualTo(dog.getDescription());
        assertThat(actual.getGroup()).isEqualTo(dog.getGroup());
    }

    @Test
    public void deleteShouldWork() throws Exception {
        service.create(dog);
        Dog actual = service.delete(dog.getId());
        assertThat(actual).isEqualTo(dog);
        assertThat(actual.getName()).isEqualTo(dog.getName());
        assertThat(actual.getDescription()).isEqualTo(dog.getDescription());
        assertThat(actual.getGroup()).isEqualTo(dog.getGroup());
    }
}
