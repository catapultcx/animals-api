package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CatsServiceTest {

    CatsService service = new CatsService();
    Cat cat = new Cat("Tom", "Bob cat");

    @Test
    public void createShouldWork() throws Exception {
        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        Cat actual = service.create(thisCat);
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
        Cat actual = service.get(cat.getId());
        assertThat(actual).isEqualTo(cat);
        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
    }

    @Test
    public void deleteShouldWork() throws Exception {
        service.create(cat);
        Cat previous = service.delete(cat.getId());
        assertThat(previous).isEqualTo(cat);
        assertThat(previous.getName()).isEqualTo(cat.getName());
        assertThat(previous.getDescription()).isEqualTo(cat.getDescription());
        assertThat(previous.getGroup()).isEqualTo(cat.getGroup());
    }

    @Test
    public void updateShouldWork() throws Exception {
        Cat thisCat = service.create(cat);
        Cat newCat = new Cat("it's changed", "it's changed");
        service.update(thisCat.getId(), newCat);
        Cat actual = service.get(thisCat.getId());
        assertThat(actual).isEqualTo(newCat);
        assertThat(actual.getName()).isEqualTo(newCat.getName());
        assertThat(actual.getDescription()).isEqualTo(newCat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(newCat.getGroup());
    }
}
