package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.CatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsController {

    @Autowired
    private CatsService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Cat> all(@RequestParam(required = false) String search) {
        if (StringUtils.hasText(search)) {
            return service.filter(search);
        } else {
            return service.all();
        }
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Cat get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Cat
    create(@RequestBody Cat cat) {
        return service.create(cat);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Cat> delete(@PathVariable String id) {
        return service.delete(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Cat>
    update(@RequestBody Cat cat) {
        return service.update(cat)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
