package cx.catapult.animals.web;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.service.AnimalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "/api/1/animals", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalsController {

    @Autowired
    private AnimalsService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<BaseAnimal> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    BaseAnimal get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    BaseAnimal create(@RequestBody BaseAnimal animal) {
        return service.create(animal);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    BaseAnimal update(@PathVariable String id, @RequestBody BaseAnimal animal) {
        if (service.get(id) == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find animal to update");
        }

        return service.update(id, animal);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (service.delete(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }
}
