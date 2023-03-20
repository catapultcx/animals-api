package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Group;
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
        Cat deleted = service.delete(cat.getId());
        assertThat(deleted).isEqualTo(cat);
        assertThat(deleted.getName()).isEqualTo(cat.getName());
        assertThat(deleted.getDescription()).isEqualTo(cat.getDescription());
        assertThat(deleted.getGroup()).isEqualTo(cat.getGroup());
    }

    @Test
    public void updateShouldWork() throws Exception {
        Cat created = service.create(cat);
        created.setName("Ginger");
        created.setDescription("Gorgeous Ginger");
        Cat updated = service.update(created);
        assertThat(updated.getId()).isEqualTo(created.getId());
        assertThat(updated.getName()).isEqualTo("Ginger");
        assertThat(updated.getDescription()).isEqualTo("Gorgeous Ginger");
        assertThat(updated.getGroup()).isEqualTo(Group.MAMMALS);
    }

    @Test
    public void filterShouldWorkForName() throws Exception {
        service.create(cat);
        service.create(new Cat("Ginger Cat", "Gorgeous Ginger"));
        assertThat(service.all().size()).isEqualTo(2);
        assertThat(service.filter("Tom").size()).isEqualTo(1);
    }

    @Test
    public void filterShouldWorkForDescription() throws Exception {
        service.create(cat);
        service.create(new Cat("Ginger Cat", "Gorgeous Ginger"));
        assertThat(service.all().size()).isEqualTo(2);
        assertThat(service.filter("Gorgeous").size()).isEqualTo(1);
    }

    @Test
    public void filterShouldWorkForBothFields() throws Exception {
        service.create(cat);
        service.create(new Cat("Ginger Cat", "Gorgeous Ginger"));
        assertThat(service.all().size()).isEqualTo(2);
        assertThat(service.filter("Cat").size()).isEqualTo(2);
    }

    @Test
    public void filterShouldWorkForBlank() throws Exception {
        service.create(cat);
        service.create(new Cat("Ginger Cat", "Gorgeous Ginger"));
        assertThat(service.all().size()).isEqualTo(2);
        assertThat(service.filter("").size()).isEqualTo(2);
    }

    @Test
    public void filterShouldWorkCaseInsensitive() throws Exception {
        service.create(cat);
        service.create(new Cat("Ginger Cat", "Gorgeous Ginger"));
        assertThat(service.all().size()).isEqualTo(2);
        assertThat(service.filter("ginger").size()).isEqualTo(1);
    }
}
