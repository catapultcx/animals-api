package cx.catapult.animals.usecase;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CatsRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GetAllCatsUseCase {
    private final CatsRepository repository;

    public GetAllCatsUseCase(CatsRepository repository) {
        this.repository = repository;
    }

    public List<Cat> execute() {
        return repository.all();
    }
} 