package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalServiceTest {

    AnimalService service = new AnimalService();
    BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Bob cat", Group.MAMMALS);

    @Test
    public void createShouldWork() throws Exception {
        BaseAnimal thisCat = new BaseAnimal();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        BaseAnimal actual = service.create(thisCat);
        assertThat(actual).isEqualTo(thisCat);
        assertThat(actual.getName()).isEqualTo(thisCat.getName());
        assertThat(actual.getDescription()).isEqualTo(thisCat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisCat.getGroup());
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
        assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
    }
}
