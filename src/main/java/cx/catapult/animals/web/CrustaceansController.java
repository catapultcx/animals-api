package cx.catapult.animals.web;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.service.CrustaceansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/crustaceans", produces = MediaType.APPLICATION_JSON_VALUE)
public class CrustaceansController {

    @Autowired
    private CrustaceansService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Crustacean> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Crustacean get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Crustacean
    create(@RequestBody Crustacean crustacean) {
        return service.create(crustacean);
    }
}
