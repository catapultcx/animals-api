package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CatsServiceTest {

    CatsService service = new CatsService();
    Cat cat = new Cat("Tom", "Bob cat");
    Cat cat2 = new Cat("Cute Cat", "A cute cat");
    Cat cat3 = new Cat("Adorable Kitten", "An adorable kitten");

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
        service.create(cat2);
        service.create(cat3);
        assertThat(service.all(null, null).size()).isEqualTo(3);
    }
    @Test
    public void allFilterByNameShouldWork() throws Exception {
        service.create(cat);
        service.create(cat2);
        service.create(cat3);
        assertThat(service.all("Cute", null).size()).isEqualTo(1);
    }

    @Test
    public void allFilterByDescriptionShouldWork() throws Exception {
        service.create(cat);
        service.create(cat2);
        service.create(cat3);
        assertThat(service.all(null, "adorable").size()).isEqualTo(1);
    }

    @Test
    public void allFilterByNameAndDescriptionShouldWork() throws Exception {
        service.create(cat);
        service.create(cat2);
        service.create(cat3);
        assertThat(service.all("Adorable", "kitten").size()).isEqualTo(1);
    }

    @Test
    public void allFilterByUnknownNameAndDescriptionShouldWork() throws Exception {
        service.create(cat);
        service.create(cat2);
        service.create(cat3);
        assertThat(service.all("Missing", "Cat").size()).isEqualTo(0);
    }
    @Test
    public void allFilterByUppercaseNameAndDescriptionShouldWork() throws Exception {
        service.create(cat);
        service.create(cat2);
        service.create(cat3);
        assertThat(service.all("CUTE", "CAT").size()).isEqualTo(1);
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
        Cat updated = new Cat("Updated Name", "Updated Description");
        Cat actual = service.update(cat.getId(), updated);
        assertThat(actual).isEqualTo(updated);
        assertThat(actual.getName()).isEqualTo(updated.getName());
        assertThat(actual.getDescription()).isEqualTo(updated.getDescription());
        assertThat(actual.getGroup()).isEqualTo(updated.getGroup());
    }
}
