package cx.catapult.animals.web;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.service.BirdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/birds", produces = MediaType.APPLICATION_JSON_VALUE)
public class BirdsController {

    @Autowired
    private BirdsService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Bird> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Bird get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Bird
    create(@RequestBody Bird cat) {
        return service.create(cat);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Bird
    update(@RequestBody Bird cat) {
        return service.update(cat);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
