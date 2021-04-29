package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.repository.AnimalsRepository;

/**
 * Unit test for {@link AnimalsService}.
 */
@ExtendWith(MockitoExtension.class)
public class AnimalsServiceTest {

	@Mock
	AnimalsRepository<BaseAnimal> repository;

	@InjectMocks
	AnimalsService<BaseAnimal> service;

	String name = "Colin";

	Cat cat = new Cat(name, "Colin cat");
	Crustacean crab = new Crustacean(name, "Colin crab");

	@Test
	public void getShouldWork() {
		cat.setId(UUID.randomUUID().toString());
		crab.setId(UUID.randomUUID().toString());
		given(repository.findById(crab.getId())).willReturn(Optional.of(crab));

		BaseAnimal actual = service.get(crab.getId());

		assertThat(actual).isEqualTo(crab);
	}

	@Test
	public void allShouldWork() {
		given(repository.findByName(name)).willReturn(Arrays.asList(cat, crab));

		Collection<? extends BaseAnimal> result = service.getByName(name);

		assertThat(result.size()).isEqualTo(2);
	}
}