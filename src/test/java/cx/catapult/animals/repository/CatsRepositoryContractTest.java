package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class CatsRepositoryContractTest {
    protected CatsRepository repository;
    protected Cat cat;

    protected abstract CatsRepository createRepository();

    @BeforeEach
    void setUp() {
        repository = createRepository();
        cat = new Cat("Tom", "Test cat");
    }

    @Test
    public void givenACat_whenCreating_thenShouldPersistIt() {
        Cat created = repository.create(cat);
        assertThat(created).isEqualTo(cat);
        assertThat(created.getId()).isEqualTo(cat.getId());
    }

    @Test
    public void givenAPersistedCat_whenGettingById_thenShouldRetrieveIt() {
        repository.create(cat);
        Cat retrieved = repository.get(cat.getId());
        assertThat(retrieved).isEqualTo(cat);
    }

    @Test
    public void givenAPersistedCat_whenListingAll_thenShouldContainIt() {
        repository.create(cat);
        assertThat(repository.all()).hasSize(1);
        assertThat(repository.all()).contains(cat);
    }
} 