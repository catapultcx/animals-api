package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.CatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static cx.catapult.animals.web.CatsMapping.CATS_API_V1;

@RestController
@RequestMapping(path = CATS_API_V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsController {

    @Autowired
    private final CatsService service;

    public CatsController(CatsService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection<Cat> getAll() {
        return service.all();
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Collection<Cat> filterBy(@RequestParam(value = "query", required = false) String query) {
        return service.filterBy(query);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Cat getById(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Cat create(@RequestBody Cat cat) {
        return service.create(cat);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Cat update(@PathVariable String id, @RequestBody Cat cat) {
        return service.update(id, cat);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Cat deleteById(@PathVariable String id) {
        return service.delete(id);
    }
}
