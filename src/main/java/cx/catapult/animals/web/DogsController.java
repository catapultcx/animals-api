package cx.catapult.animals.web;

import cx.catapult.animals.domain.Dog;
import cx.catapult.animals.service.DogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/1/dogs", produces = MediaType.APPLICATION_JSON_VALUE)
public class DogsController {

    @Autowired
    private DogsService service;

    private final Logger log = Logger.getLogger(DogsController.class.getName());

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Dog create(@RequestBody Dog dog) {
        final Dog created = service.create(dog);
        log.info("Created dog. ID: " + created.getId());
        return created;
    }

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

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public @ResponseBody
    Dog delete(@PathVariable String id) {
        return service.delete(id).orElse(null);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
    public @ResponseBody
    Dog update(@PathVariable String id, @RequestBody Dog dog) {
        return service.update(id, dog).orElse(null);
    }
}