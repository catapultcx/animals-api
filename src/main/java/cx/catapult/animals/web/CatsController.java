package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.usecase.CreateCatUseCase;
import cx.catapult.animals.usecase.GetAllCatsUseCase;
import cx.catapult.animals.usecase.GetCatUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsController {
    private final CreateCatUseCase createCatUseCase;
    private final GetCatUseCase getCatUseCase;
    private final GetAllCatsUseCase getAllCatsUseCase;

    public CatsController(CreateCatUseCase createCatUseCase,
                         GetCatUseCase getCatUseCase,
                         GetAllCatsUseCase getAllCatsUseCase) {
        this.createCatUseCase = createCatUseCase;
        this.getCatUseCase = getCatUseCase;
        this.getAllCatsUseCase = getAllCatsUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cat create(@RequestBody Cat cat) {
        return createCatUseCase.execute(cat);
    }

    @GetMapping("/{id}")
    public Cat get(@PathVariable String id) {
        return getCatUseCase.execute(id);
    }

    @GetMapping
    public List<Cat> all() {
        return getAllCatsUseCase.execute();
    }
}
