package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.exception.AnimalNotFoundException;

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
	public void createShouldFailIfNameOrDescriptionIsMissing() throws Exception {
		Cat thisCat = new Cat();
		assertThatThrownBy(() -> service.create(thisCat)).isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Name and description are required fields");
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
	public void getShouldFailIfIdIsInvalid() {
		String id = "id";
		assertThatThrownBy(() -> service.get(id)).isInstanceOf(AnimalNotFoundException.class)
				.hasMessageContaining(String.format("Animal not found with id: %s", id));
	}

	@Test
	public void deleteShouldWork() throws Exception {
		service.create(cat);

		service.delete(cat.getId());

		assertThat(service.all().size()).isEqualTo(0);
	}

	@Test
	public void deleteShouldFail() throws Exception {
		String id = "id";
		assertThatThrownBy(() -> service.delete(id)).isInstanceOf(AnimalNotFoundException.class)
				.hasMessageContaining(String.format("Animal not found with id: %s", id));
	}
}
