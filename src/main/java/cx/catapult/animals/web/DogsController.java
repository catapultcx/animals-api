package cx.catapult.animals.web;

import cx.catapult.animals.domain.Dog;
import cx.catapult.animals.service.DogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/dogs", produces = MediaType.APPLICATION_JSON_VALUE)
public class DogsController {

    @Autowired
    private DogsService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Dog> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Dog get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Dog
    create(@RequestBody Dog dog) {
        return service.create(dog);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Dog delete(@PathVariable String id) {
        return service.delete(id);
    }
}
