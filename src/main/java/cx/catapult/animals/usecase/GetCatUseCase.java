package cx.catapult.animals.usecase;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CatsRepository;
import org.springframework.stereotype.Component;

@Component
public class GetCatUseCase {
    private final CatsRepository repository;

    public GetCatUseCase(CatsRepository repository) {
        this.repository = repository;
    }

    public Cat execute(String id) {
        return repository.get(id);
    }
} 