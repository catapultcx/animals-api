package cx.catapult.animals.web;

import cx.catapult.animals.domain.Turtle;
import cx.catapult.animals.service.TurtlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/turtles", produces = MediaType.APPLICATION_JSON_VALUE)
public class TurtlesController {

    @Autowired
    private TurtlesService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Turtle> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Turtle get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Turtle
    create(@RequestBody Turtle turtle) {
        return service.create(turtle);
    }
}
