package cx.catapult.animals.web;

import cx.catapult.animals.domain.Fish;
import cx.catapult.animals.service.FishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/fishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class FishesController {

    @Autowired
    private FishesService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Fish> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Fish get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Fish
    create(@RequestBody Fish fish) {
        return service.create(fish);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Fish
    update(@PathVariable String id, @RequestBody Fish fish) {
        return service.update(id, fish);
    }

    @DeleteMapping(value = "/{id}")
    public
    void delete(@PathVariable String id) {
        service.delete(id);
    }
}
