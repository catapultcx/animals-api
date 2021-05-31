package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;

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
    public void updateShouldWork() {
        Cat initialInsect = service.create(cat);
        Cat replacement = new Cat("Nota", "Grasshopper");
        Cat finalCat = service.update(initialInsect.getId(), replacement);
        assertThat(finalCat.getDescription()).isEqualTo(replacement.getDescription());
        assertThat(finalCat.getName()).isEqualTo(replacement.getName());
    }
}
