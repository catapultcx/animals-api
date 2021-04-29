package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import cx.catapult.animals.domain.Cat;

/**
 * Unit test for {@link CatsService}.
 */
public class CatsServiceTest {

    CatsService service = new CatsService();
    Cat cat = new Cat("Tom", "Bob cat");

    @Test
    public void createShouldWork() {
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
    public void allShouldWork() {
        service.create(cat);

        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        service.create(cat);

        Cat actual = service.get(cat.getId());

        assertThat(actual).isEqualTo(cat);
        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
    }

	@Test
	public void getShouldWorkGivenUnknownCat() {
		Cat actual = service.get("unknown-id");

		assertThat(actual).isNull();
	}

	@Test
	public void deleteShouldWork() {
		service.create(cat);

		boolean deleted = service.delete(cat.getId());

		assertThat(deleted).isTrue();
	}

	@Test
	public void deleteShouldWorkGivenUnknownCat() {
		boolean deleted = service.delete("unknown-id");

		assertThat(deleted).isFalse();
	}
}
