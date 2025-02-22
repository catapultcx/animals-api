package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeCatsRepositoryTest {
    private FakeCatsRepository repository;
    private Cat cat;

    @BeforeEach
    void setUp() {
        repository = new FakeCatsRepository();
        cat = new Cat("Tom", "Test cat");
    }

    @Test
    public void shouldCreateCat() {
        Cat created = repository.create(cat);
        assertThat(created).isEqualTo(cat);
        assertThat(created.getId()).isEqualTo(cat.getId());
    }

    @Test
    public void shouldGetCat() {
        repository.create(cat);
        Cat retrieved = repository.get(cat.getId());
        assertThat(retrieved).isEqualTo(cat);
    }

    @Test
    public void shouldListAllCats() {
        repository.create(cat);
        assertThat(repository.all()).hasSize(1);
        assertThat(repository.all()).contains(cat);
    }

    @Test
    public void shouldInitializeWithDefaultCats() {
        repository.initialize();
        assertThat(repository.all()).hasSize(7);
        assertThat(repository.all())
            .extracting(Cat::getName)
            .contains("Tom", "Jerry", "Bili", "Smelly", "Tiger", "Tigger", "Garfield");
    }
} 