package cx.catapult.animals.usecase;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.FakeCatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCatUseCaseTest {
    private FakeCatsRepository repository;
    private GetCatUseCase useCase;
    private Cat cat;

    @BeforeEach
    void setUp() {
        repository = new FakeCatsRepository();
        useCase = new GetCatUseCase(repository);
        cat = new Cat("Tom", "Test cat");
        repository.create(cat);
    }

    @Test
    void givenAnExistingCat_whenGettingById_thenShouldReturnIt() {
        Cat result = useCase.execute(cat.getId());
        
        assertThat(result).isEqualTo(cat);
    }
} 