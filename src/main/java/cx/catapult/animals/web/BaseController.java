package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.exceptions.AnimalException;
import cx.catapult.animals.service.CatsService;
import cx.catapult.animals.service.Service;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class BaseController<T extends Animal> {

    private Service<T> service;

    public BaseController(Service<T> service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<T> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    T get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    T
    create(@RequestBody T animal) {
        return service.create(animal);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody
    void delete(@PathVariable String id) throws AnimalException {
       service.remove(id);
    }
}
