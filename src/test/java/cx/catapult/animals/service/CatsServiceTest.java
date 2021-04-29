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

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CatsRepository;

/**
 * Unit test for {@link CatsService}.
 */
@ExtendWith(MockitoExtension.class)
public class CatsServiceTest {

	@Mock
	CatsRepository repository;

	@InjectMocks
    CatsService service;

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
		verify(repository).save(thisCat);
    }

    @Test
    public void allShouldWork() {
		given(repository.getAll()).willReturn(Collections.singletonList(cat));

        service.create(cat);

        assertThat(service.all().size()).isEqualTo(1);
		verify(repository).getAll();
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
		String id = "unknown-id";
		given(repository.findById(id)).willReturn(Optional.empty());

		Cat actual = service.get(id);

		assertThat(actual).isNull();
		verify(repository).findById(id);
	}

	@Test
	public void updateShouldWork() {
		service.create(cat);
		Cat updated = new Cat("Mog", "Mog the cat");
		given(repository.findById(cat.getId())).willReturn(Optional.of(cat));

		Cat result = service.update(cat.getId(), updated);

		assertThat(result.getId()).isEqualTo(cat.getId());
		assertThat(result.getName()).isEqualTo(cat.getName());
		assertThat(result.getDescription()).isEqualTo(cat.getDescription());
		assertThat(result.getGroup()).isEqualTo(cat.getGroup());
		verify(repository, times(2)).save(result);
	}

	@Test
	public void updateShouldWorkGivenUnknownCrab() {
		service.create(cat);
		String unknownId = "unknown-id";
		given(repository.findById(unknownId)).willReturn(Optional.empty());
		Cat updated = new Cat("Molly", "Molly the unknown cat");

		Cat result = service.update(unknownId, updated);

		assertThat(result).isNull();
	}

	@Test
	public void deleteShouldWork() {
		service.create(cat);

		boolean deleted = service.delete(cat.getId());

		assertThat(deleted).isTrue();
		verify(repository).deleteById(cat.getId());
	}

	@Test
	public void deleteShouldWorkGivenUnknownCat() {
		String id = "unknown-id";
		doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(id);

		boolean deleted = service.delete(id);

		assertThat(deleted).isFalse();
	}
}
