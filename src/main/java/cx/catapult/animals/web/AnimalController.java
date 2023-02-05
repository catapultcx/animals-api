package cx.catapult.animals.web;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/api/1/animals", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalController {

    @Autowired
    private AnimalService service;

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
    BaseAnimal
    create(@RequestBody BaseAnimal baseAnimal) {
        return service.create(baseAnimal);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void
    update(@RequestBody BaseAnimal baseAnimal) {
        service.update(baseAnimal);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping(value = "/filter")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<BaseAnimal> filter(@RequestParam(required = false) String name, @RequestParam(required = false) String color
            , @RequestParam(required = false) String type, @RequestParam(required = false) String description) {
        return service.filter(name, color, type, description);
    }
}
