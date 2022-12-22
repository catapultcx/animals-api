package cx.catapult.animals.web;

import cx.catapult.animals.domain.Generic;
import cx.catapult.animals.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/generic", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenericController {

    @Autowired
    private GenericService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Generic> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Generic get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Generic create(@RequestBody Generic generic) {
        return service.create(generic);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    boolean delete(@PathVariable String id) {
        return service.delete(id);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Generic update(@RequestBody Generic generic) {
        return service.update(generic);
    }

}
