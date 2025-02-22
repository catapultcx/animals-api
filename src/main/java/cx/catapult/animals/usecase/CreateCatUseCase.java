package cx.catapult.animals.usecase;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CatsRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateCatUseCase {
    private final CatsRepository repository;

    public CreateCatUseCase(CatsRepository repository) {
        this.repository = repository;
    }

    public Cat execute(Cat cat) {
        return repository.create(cat);
    }
} 