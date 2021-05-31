package cx.catapult.animals.web;

import cx.catapult.animals.domain.Insect;
import cx.catapult.animals.service.InsectsService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/1/insects", produces = MediaType.APPLICATION_JSON_VALUE)
public class InsectsController {

    @Autowired
    private InsectsService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Insect> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Insect get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Insect
    create(@RequestBody Insect insect) {
        return service.create(insect);
    }
}
