package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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
    BaseAnimal create(@RequestBody BaseAnimal animal) {
        return service.create(animal);
    }
}
