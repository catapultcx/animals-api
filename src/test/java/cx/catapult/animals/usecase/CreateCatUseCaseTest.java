package cx.catapult.animals.usecase;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.FakeCatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateCatUseCaseTest {
    private FakeCatsRepository repository;
    private CreateCatUseCase useCase;
    private Cat cat;

    @BeforeEach
    void setUp() {
        repository = new FakeCatsRepository();
        useCase = new CreateCatUseCase(repository);
        cat = new Cat("Tom", "Test cat");
    }

    @Test
    void givenANewCat_whenCreating_thenShouldPersistAndReturnIt() {
        Cat result = useCase.execute(cat);
        
        assertThat(result).isEqualTo(cat);
        assertThat(repository.get(cat.getId())).isEqualTo(cat);
    }
} 