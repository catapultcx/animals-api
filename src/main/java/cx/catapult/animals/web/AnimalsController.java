package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.AnimalImpl;
import cx.catapult.animals.service.AnimalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/animals", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalsController {

    @Autowired
    private AnimalsService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Animal> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Animal get(@PathVariable String id) {
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody
    Animal delete(@PathVariable String id) {
        return service.delete(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Animal
    create(@RequestBody AnimalImpl animal) {
        return service.create(animal);
    }

	@PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Animal
    save(@RequestBody AnimalImpl animal) {
        return service.save(animal);
    }
}
