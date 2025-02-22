package cx.catapult.animals.usecase;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.FakeCatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllCatsUseCaseTest {
    private FakeCatsRepository repository;
    private GetAllCatsUseCase useCase;
    private List<Cat> cats;

    @BeforeEach
    void setUp() {
        repository = new FakeCatsRepository();
        useCase = new GetAllCatsUseCase(repository);
        repository.create(new Cat("Tom", "Test cat"));
        repository.create(new Cat("Jerry", "Another cat"));
        cats = repository.all();
    }

    @Test
    void givenCatsExist_whenGettingAll_thenShouldReturnAllCats() {
        List<Cat> results = useCase.execute();
        
        assertThat(results).hasSameElementsAs(cats);
    }
} 