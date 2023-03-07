package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.CatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsController {

    @Autowired
    private CatsService service;
    private static final String ALLOWED_URL = "http://localhost:3000/";

    @GetMapping(value = "", produces = "application/json")
    @CrossOrigin(origins = ALLOWED_URL)
    public @ResponseBody Collection<Cat> all(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        Collection<Cat> cats = service.all();

        if (name != null && !name.isEmpty()) {
            cats = cats.stream()
                    .filter(cat -> cat.getName().contains(name))
                    .collect(Collectors.toList());
        }

        if (description != null && !description.isEmpty()) {
            cats = cats.stream()
                    .filter(cat -> cat.getDescription().contains(description))
                    .collect(Collectors.toList());
        }

        return cats;
    }

    @GetMapping(value = "/{id}")
    @CrossOrigin(origins = ALLOWED_URL)
    public @ResponseBody Cat get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ALLOWED_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Cat create(@RequestBody Cat cat) {
        return service.create(cat);
    }

    @DeleteMapping(value = "/{id}")
    @CrossOrigin(origins = ALLOWED_URL)
    public @ResponseBody Cat delete(@PathVariable String id) {
        return service.remove(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ALLOWED_URL)
    public @ResponseBody Cat update(@PathVariable String id, @RequestBody Cat cat) {
        Cat existingCat = service.get(id);
        if (existingCat == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return service.save(id, cat.getName(), cat.getDescription());
    }

}
