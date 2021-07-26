package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CatRepository;
import cx.catapult.animals.service.CatsService;
import java.util.Collection;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsController {

    private final CatRepository catRepository;

    private final CatsService service;

    public CatsController(CatsService service, CatRepository catRepository) {
        this.service = service;
        this.catRepository = catRepository;
    }

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Cat> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Cat get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Cat
    create(@RequestBody Cat cat) {
        return service.create(cat);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Cat update(@PathVariable(value = "id") UUID catId, @RequestBody Cat catDetails) {
        return service.update(catDetails, catId);
    }

}
