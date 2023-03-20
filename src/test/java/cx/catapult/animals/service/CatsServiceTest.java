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
        assertThat(service.all("").size()).isEqualTo(1);
        assertThat(service.all("To").size()).isEqualTo(1);
        assertThat(service.all("Hello").size()).isEqualTo(0);
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
    public void updateShouldWork() throws Exception {
        cat.setName("updated name"); 
        cat.setDescription("updated description");
        Cat updatedCate = service.update(cat.getId(), cat);
        assertThat(updatedCate).isEqualTo(cat);
        assertThat(updatedCate.getName()).isEqualTo("updated name");
        assertThat(updatedCate.getDescription()).isEqualTo("updated description");
    }

    @Test
    public void deleteShouldWork() throws Exception {
        service.create(cat);
        assertThat(service.all("").size()).isEqualTo(1);
        service.delete(cat.getId());
        assertThat(service.all("").size()).isEqualTo(0);
    }
}
