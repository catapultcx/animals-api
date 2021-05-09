package cx.catapult.animals.web;

import cx.catapult.animals.service.ReptilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import cx.catapult.animals.domain.Reptile;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/reptiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReptilesController {

    @Autowired
    private ReptilesService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Reptile> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Reptile get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Reptile
    create(@RequestBody Reptile reptile) {
        return service.create(reptile);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Reptile
    update(@PathVariable String id, @RequestBody Reptile reptile) {
        return service.update(id, reptile);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}