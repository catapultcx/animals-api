package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.repository.CrustaceansRepository;

/**
 * Unit test for {@link CrustaceansService}.
 */
@ExtendWith(MockitoExtension.class)
public class CrustaceansServiceTest {

	@Mock
	CrustaceansRepository repository;

	@InjectMocks
	CrustaceansService service;

	Crustacean crab = new Crustacean("Crusty", "Crusty the crab");

	@Test
	public void createShouldWork() {
		Crustacean thisCrab = new Crustacean();
		thisCrab.setName("Crabby");
		thisCrab.setDescription("Crabby the Crab");

		Crustacean actual = service.create(thisCrab);

		assertThat(actual).isEqualTo(thisCrab);
		assertThat(actual.getName()).isEqualTo(thisCrab.getName());
		assertThat(actual.getDescription()).isEqualTo(thisCrab.getDescription());
		assertThat(actual.getGroup()).isEqualTo(thisCrab.getGroup());
		verify(repository).save(thisCrab);
	}

	@Test
	public void allShouldWork() {
		service.create(crab);
		given(repository.getAll()).willReturn(Collections.singletonList(crab));

		assertThat(service.all().size()).isEqualTo(1);
		verify(repository).getAll();
	}

	@Test
	public void getShouldWork() {
		service.create(crab);

		Crustacean actual = service.get(crab.getId());

		assertThat(actual).isEqualTo(crab);
		assertThat(actual.getName()).isEqualTo(crab.getName());
		assertThat(actual.getDescription()).isEqualTo(crab.getDescription());
		assertThat(actual.getGroup()).isEqualTo(crab.getGroup());
	}

	@Test
	public void getShouldWorkGivenUnknownCrustacean() {
		String id = "unknown-id";
		given(repository.findById(id)).willReturn(Optional.empty());

		Crustacean actual = service.get(id);

		assertThat(actual).isNull();
		verify(repository).findById(id);
	}

	@Test
	public void updateShouldWork() {
		service.create(crab);
		Crustacean updated = new Crustacean("Colin", "Colin the crab");
		given(repository.findById(crab.getId())).willReturn(Optional.of(crab));

		Crustacean result = service.update(crab.getId(), updated);

		assertThat(result.getId()).isEqualTo(crab.getId());
		assertThat(result.getName()).isEqualTo(crab.getName());
		assertThat(result.getDescription()).isEqualTo(crab.getDescription());
		assertThat(result.getGroup()).isEqualTo(crab.getGroup());
		verify(repository, times(2)).save(result);
	}

	@Test
	public void updateShouldWorkGivenUnknownCrab() {
		service.create(crab);
		String unknownId = "unknown-id";
		given(repository.findById(unknownId)).willReturn(Optional.empty());
		Crustacean updated = new Crustacean("Colin", "Colin the unknown crab");

		Crustacean result = service.update("unknown-id", updated);

		assertThat(result).isNull();
	}

	@Test
	public void deleteShouldWork() {
		service.create(crab);

		boolean deleted = service.delete(crab.getId());

		assertThat(deleted).isTrue();
		verify(repository).deleteById(crab.getId());
	}

	@Test
	public void deleteShouldWorkGivenUnknownCrustacean() {
		String id = "unknown-id";
		doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(id);

		boolean deleted = service.delete(id);

		assertThat(deleted).isFalse();
	}
}