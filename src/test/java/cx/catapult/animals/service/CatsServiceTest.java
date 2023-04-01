package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

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
    public void updateShouldWork() throws Exception {
        service.create(cat);
        Cat actual = service.get(cat.getId());
        assertThat(actual).isEqualTo(cat);
        actual.setName("nameUpdated");
        actual.setDescription("descUpdated");
        Assertions.assertTrue(service.update(actual));

        Cat updated = service.get(actual.getId());
        System.out.println(updated);
        assertThat(actual.getName()).isEqualTo(updated.getName());
        assertThat(actual.getDescription()).isEqualTo(updated.getDescription());
    }

    @Test
    public void updateShouldNotWork() throws Exception {
        service.create(cat);
        Cat actual = service.get(cat.getId());
        assertThat(actual).isEqualTo(cat);
        actual.setName("nameUpdated");
        actual.setDescription("descUpdated");
        actual.setId("some invalid id");
        Assertions.assertFalse(service.update(actual));
    }

    @Test
    public void deleteShouldWork() throws Exception {
        Cat created = service.create(cat);
        assertThat(service.all().size()).isEqualTo(1);
        boolean deleted = service.delete(created.getId());
        assertThat(deleted).isEqualTo(true);
        assertThat(service.all().size()).isEqualTo(0);
    }

    @Test
    public void searchShouldWork() throws Exception {
        Cat first = service.create(cat);
        assertThat(service.all().size()).isEqualTo(1);

        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        Cat second = service.create(thisCat);
        assertThat(service.all().size()).isEqualTo(2);

        Collection<Cat> results = service.search("", "");
        assertThat(results.size()).isEqualTo(2);
        results = service.search("tom", "");
        assertThat(results.size()).isEqualTo(1);
        results = service.search("", "Mouse");
        assertThat(results.size()).isEqualTo(1);
        results = service.search("", "cat");
        assertThat(results.size()).isEqualTo(2);
    }
}
