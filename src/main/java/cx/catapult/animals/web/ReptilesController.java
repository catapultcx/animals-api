package cx.catapult.animals.web;

import cx.catapult.animals.service.ReptilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import cx.catapult.animals.domain.Reptile;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/reptiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReptilesController {

    @Autowired
    private ReptilesService service;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Reptile
    create(@RequestBody Reptile reptile) {
        return service.create(reptile);
    }
}