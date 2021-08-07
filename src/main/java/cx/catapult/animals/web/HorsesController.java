package cx.catapult.animals.web;

import cx.catapult.animals.domain.Horse;
import cx.catapult.animals.service.HorsesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/horses", produces = MediaType.APPLICATION_JSON_VALUE)
public class HorsesController {

    @Autowired
    private HorsesService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Horse> all() {
        return service.all();
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Horse create(@RequestBody Horse horse) {
        return service.create(horse);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Horse get(@PathVariable String id) {
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    void delete(@PathVariable String id) {
        service.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    void update(@RequestBody Horse horse) {
        service.update(horse);
    }

}
