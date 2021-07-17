package cx.catapult.animals.web;

import cx.catapult.animals.domain.Arachnid;
import cx.catapult.animals.service.ArachnidsService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/1/arachnids", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArachnidsController {

    @Autowired
    private ArachnidsService service;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Arachnid> all() {
        return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Arachnid get(@PathVariable String id) {
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody
    Arachnid delete(@PathVariable String id) {
        return service.delete(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Arachnid
    create(@RequestBody Arachnid arachnid) {
        return service.create(arachnid);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Arachnid
    update(@RequestBody Arachnid arachnid, @PathVariable String id) {
        return service.update(id, arachnid);
    }
}
