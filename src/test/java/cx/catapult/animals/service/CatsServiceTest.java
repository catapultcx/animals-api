package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cx.catapult.animals.domain.Cat;

public class CatsServiceTest {

    CatsService service;
    Cat cat;

    @BeforeEach
    public void initilize() {
	service = new CatsService();
	cat = new Cat("Tom", "Bob cat");
    }

    @Test
    public void createShouldWork() {
	Cat thisCat = new Cat();
	thisCat.setName("Jerry");
	thisCat.setDescription("Mouse Cat");
	Cat actual = service.create(thisCat);
	assertThat(actual).isEqualTo(thisCat);
	assertThat(actual.getId()).isNotNull();
    }

    @Test
    public void allShouldWork() {
	service.create(cat);
	assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
	service.create(cat);
	Cat actual = service.get(cat.getId());
	assertThat(actual).isNotNull().isEqualTo(cat);
    }

    @Test
    public void getThatDoesNotExitsShouldWork() {
	Cat actual = service.get("NotExist");
	assertThat(actual).isNull();
    }

    @Test
    public void updateShouldWork() {
	Cat entity = service.create(cat);
	Cat updated = entity.toBuilder().description("American Shorthair").build();
	Cat actual = service.update(updated);
	assertThat(actual).isNotNull().isEqualTo(updated);
    }

    @Test
    public void removeShouldWork() {
	final Cat created = service.create(cat);
	final Cat removed = service.remove(created.getId());
	assertThat(removed).isNotNull().isEqualTo(created);
	Cat actual = service.get(cat.getId());
	assertThat(actual).isNull();
    }

    @Test
    public void removeThatDoesNotExitsShouldWork() {
	final Cat removed = service.remove("NotExist");
	assertThat(removed).isNull();
    }
}
