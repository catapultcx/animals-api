package cx.catapult.animals.web;

import cx.catapult.animals.domain.BaseAmphibian;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/1/amphibians", produces = MediaType.APPLICATION_JSON_VALUE)
public class AmphibianController {

    private final Service<BaseAmphibian> amphibianService;

    public AmphibianController(final Service amphibianService) {
        this.amphibianService = amphibianService;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    BaseAmphibian create(@RequestBody BaseAmphibian amphibian) {
        return amphibianService.create(amphibian);
    }
}
