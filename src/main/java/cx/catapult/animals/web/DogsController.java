package cx.catapult.animals.web;

import cx.catapult.animals.domain.Dog;
import cx.catapult.animals.service.DogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/1/dogs", produces = MediaType.APPLICATION_JSON_VALUE)
public class DogsController {

    @Autowired
    private DogsService service;

    private final Logger log = Logger.getLogger(DogsController.class.getName());

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Dog create(@RequestBody Dog dog) {
        final Dog created = service.create(dog);
        log.info("Created dog. ID: " + created.getId());
        return created;
    }
}
