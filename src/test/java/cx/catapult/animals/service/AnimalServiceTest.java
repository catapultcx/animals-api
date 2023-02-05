package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    public void updateShouldWork() throws Exception {
        BaseAnimal thisCat = new BaseAnimal();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        thisCat.setType("Cat");
        thisCat.setColor("Grey");
        BaseAnimal actual = service.create(thisCat);

        BaseAnimal catUpdate = new BaseAnimal();
        catUpdate.setId(actual.getId());
        catUpdate.setName("Tom");
        catUpdate.setDescription("Mouse Cat");
        catUpdate.setType("Cat");
        catUpdate.setColor("Grey");
        service.update(catUpdate);
        BaseAnimal updatedCat = service.get(actual.getId());

                assertThat(actual.getId()).isEqualTo(updatedCat.getId());
        assertThat(catUpdate.getName()).isEqualTo(updatedCat.getName());
        assertThat(catUpdate.getColor()).isEqualTo(updatedCat.getColor());
        assertThat(catUpdate.getType()).isEqualTo(updatedCat.getType());
        assertThat(updatedCat.getDescription()).isEqualTo(actual.getDescription());
        assertThat(updatedCat.getGroup()).isEqualTo(actual.getGroup());
    }

    @Test
    public void daleteShouldWork() throws Exception {
        BaseAnimal thisCat = new BaseAnimal();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        thisCat.setType("Cat");
        thisCat.setColor("Grey");
        BaseAnimal actual = service.create(thisCat);

        assertNotNull(service.get(actual.getId()));

        service.delete(actual.getId());

        assertNull(service.get(actual.getId()));
    }
}
