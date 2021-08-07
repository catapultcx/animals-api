package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CatsServiceTest {

    @Autowired
    CatsService service;
    Cat cat = new Cat("Tom", "Bob cat");

    @Test
    public void createShouldWork() throws Exception {
        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        Cat actual = service.create(thisCat);
        assertThat(actual.getName()).isEqualTo(thisCat.getName());
        assertThat(actual.getDescription()).isEqualTo(thisCat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisCat.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        int size = service.all().size();
        service.create(cat);
        assertThat(service.all().size()).isEqualTo(size+1);
    }

    @Test
    public void shouldRemoveCat() throws Exception {
        Cat cat = service.create(this.cat);
        boolean isDeleted = service.delete(cat.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    public void getShouldWork() throws Exception {
        Cat cat = service.create(this.cat);
        Cat actual = service.get(cat.getId());
        assertThat(actual.getId()).isEqualTo(cat.getId());
        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
    }

    @Test
    public void shouldUpdate() throws Exception {
        Cat cat = service.create(this.cat);
        int sizeBeforeUpdate = service.all().size();
        service.update(cat.getId(), cat);
        int sizeAfterUpdate = service.all().size();
        assertThat(sizeBeforeUpdate).isEqualTo(sizeAfterUpdate);
    }
}
