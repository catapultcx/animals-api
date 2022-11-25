package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalServiceTest {

    AnimalService service = new AnimalService();
    BaseAnimal cat = new Cat("Tom", "Bob cat", "Black");

    @Test
    public void createShouldWork() throws Exception {
        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        thisCat.setColour("Black");
        BaseAnimal actual = service.create(thisCat);
        assertThat(actual).isEqualTo(thisCat);
        assertThat(actual.getName()).isEqualTo(thisCat.getName());
        assertThat(actual.getDescription()).isEqualTo(thisCat.getDescription());
        assertThat(actual.getClassification()).isEqualTo(thisCat.getClassification());
        assertThat(actual.getColour()).isEqualTo(thisCat.getColour());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(cat);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(cat);
        BaseAnimal actual = service.get(cat.getId());
        assertThat(actual).isEqualTo(cat);
        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getClassification()).isEqualTo(cat.getClassification());
    }
}
