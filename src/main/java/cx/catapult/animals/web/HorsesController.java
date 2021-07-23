package cx.catapult.animals.web;

import cx.catapult.animals.domain.Horse;
import cx.catapult.animals.service.HorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/horses", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class HorsesController {

    @Autowired
    private HorseService service;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Horse
    create(@RequestBody @Valid Horse horse) {
        return service.create(horse);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Horse get(@PathVariable @Valid @NotBlank(message = "Id cannot be null") String id) {
        return service.get(id);
    }

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Horse> all() {
        return service.all();
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    void update(@RequestBody @Valid Horse horse) {
        service.update(horse);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody
    void delete(@PathVariable @Valid @NotBlank(message = "Id cannot be null") String id) {
        service.delete(id);
    }
}
