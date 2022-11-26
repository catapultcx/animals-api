package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/animals", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalsController {

    @Autowired
    private AnimalService service;

    @GetMapping(value = "", produces = "application/json")
    @ResponseBody
    public Collection<Animal> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Animal get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Animal create(@RequestBody Animal animal) {
        return service.create(animal);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Animal delete(@PathVariable String id) {
        return service.delete(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Animal delete(@PathVariable String id, @RequestBody Animal animalToUpdate) {
        return service.update(id, animalToUpdate);
    }
}
