package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.CatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsController {

    @Autowired
    private CatsService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Cat> all(@RequestParam(required = false) String keyWords) {
        return service.all(keyWords);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public @ResponseBody
    Collection<Cat> delete(@PathVariable String id) {
        return service.delete(id);
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

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Cat
    update(@PathVariable String id, @RequestBody Cat cat) {
        return service.update(id, cat);
    }
}
