package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.CatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class CatsController {

    @Autowired
    private CatsService service;

    @GetMapping(value = "/viewAllCats", produces = "application/json")
    public @ResponseBody Collection<Cat> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody Cat get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "/createCat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Cat create(@RequestBody Cat cat) {
        return service.create(cat);
    }

    @DeleteMapping(value = "/removeCat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> remove(@RequestBody Cat cat) {
        try {
            service.remove(cat.getId());
            return new ResponseEntity<String>("Cat is removed", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<String>("Cat not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/updateCat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Cat update(@RequestBody Cat cat) {
        return service.update(cat);
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public @ResponseBody Collection<Cat> filter(@RequestParam String text) {
        return service.filter(text);
    }
}
