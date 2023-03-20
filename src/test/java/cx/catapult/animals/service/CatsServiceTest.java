package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;

import org.junit.jupiter.api.Assertions;
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
    public void deleteShouldWork() {
        // When
        service.remove(cat.getId());
        assertThat(service.all().size()).isEqualTo(0);
    }

    @Test
    public void deleteShouldWork_ValueNotFound() {
        // Given
        service.create(cat);
        assertThat(service.all().size()).isEqualTo(1);

        // When
        service.remove("Dummy");
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void updateShouldWork() {
        // Given
        service.create(cat);
        assertThat(service.all().size()).isEqualTo(1);

        Cat existingCat = service.get(cat.getId());
        existingCat.setName("Pussy Cat");
        existingCat.setDescription("Pussy Cat");

        // When
        service.update(existingCat);
        Cat result = service.get(cat.getId());

        // Then
        assertThat(result.getName()).isEqualTo(existingCat.getName());
        assertThat(result.getDescription()).isEqualTo(existingCat.getDescription());
    }

    @Test
    public void updateShouldWork_ValueNotFound() {
        // Given
        service.create(cat);
        assertThat(service.all().size()).isEqualTo(1);

        Cat existingCat = service.get(cat.getId());
        existingCat.setName("Pussy Cat");
        existingCat.setDescription("Pussy Cat");
        existingCat.setId("Dummy");

        // When
        service.update(existingCat);
        Cat result = service.get(cat.getId());

        // Then
        Assertions.assertThrows(NullPointerException.class, () -> {
            result.getId();
        });
    }

    @Test
    public void filterShouldWork_ByName() {
        service.create(cat);
        service.create(new Cat("Garfield", "Lazy Cat"));
        assertThat(service.all().size()).isEqualTo(2);
        assertThat(service.filter("Lazy").size()).isEqualTo(1);

    }

    @Test
    public void filterShouldWork_ByDescription() {
        service.create(cat);
        service.create(new Cat("Garfield", "Lazy Cat"));
        assertThat(service.all().size()).isEqualTo(2);
        assertThat(service.filter("Lazy Cat").size()).isEqualTo(1);
    }

}
