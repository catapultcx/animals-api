package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class CatsServiceTest {

    CatsService service;
    Cat cat = new Cat("Tom", "Bob cat");

    @BeforeEach
    public void init() {
        service = new CatsService();
    }

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

        Cat actual = service.get(cat.getId());
        assertThat(actual).isEqualTo(cat);

        service.delete(cat.getId());
        Cat deleted = service.get(cat.getId());
        assertThat(deleted).isNull();
    }

    @Test
    public void updateShouldWorkForExistingCat() throws Exception {
        service.create(cat);
        cat.setName("Modified");
        Cat result = service.update(cat).orElseThrow(() -> new RuntimeException("Shouldn't be empty"));
        assertThat(result).isEqualTo(cat);
    }

    @Test
    public void updateShouldWorkForNonExistingCat() throws Exception {
        cat.setName("Modified");
        Cat result = service.update(cat).orElse(null);
        assertThat(result).isNull();
    }

    @Test
    public void filterShouldWork() throws Exception {
        service.initialize();
        assertThat(service.all().size()).isEqualTo(7);
        Collection<Cat> result = service.filter("Bili");
        assertThat(result.size()).isEqualTo(1);
    }
}
