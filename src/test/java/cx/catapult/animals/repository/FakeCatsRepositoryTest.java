package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeCatsRepositoryTest extends CatsRepositoryContractTest {
    
    @Override
    protected CatsRepository createRepository() {
        return new FakeCatsRepository();
    }

    @Test
    public void shouldInitializeWithDefaultCats() {
        ((FakeCatsRepository) repository).initialize();
        assertThat(repository.all()).hasSize(7);
        assertThat(repository.all())
            .extracting(Cat::getName)
            .contains("Tom", "Jerry", "Bili", "Smelly", "Tiger", "Tigger", "Garfield");
    }
} 