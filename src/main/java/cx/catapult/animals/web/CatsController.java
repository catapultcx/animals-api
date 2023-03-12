package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.CatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsController {

    @Autowired
    private CatsService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Cat> all(@RequestParam Optional<String> name, @RequestParam Optional<String> description) {
        if(name.isPresent() || description.isPresent())
            return service.getByFilter(
                name.isPresent() ? name.get() : "",
                description.isPresent() ? description.get() : ""
            );
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
    create(@Valid @RequestBody Cat cat) {
        return service.create(cat);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Cat update(@Valid @RequestBody Cat cat) {
        if(cat.getId()==null || cat.getId().isEmpty())
            return cat;
        return service.update(cat);
    }

    @DeleteMapping(value = "/{id}")
    public boolean delete(@PathVariable String id) {
        return service.delete(id);
    }

}
