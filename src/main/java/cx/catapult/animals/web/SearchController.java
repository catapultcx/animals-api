package cx.catapult.animals.web;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

    @Autowired
    private SearchService service;

    @GetMapping(value = "/{query}", produces = "application/json")
    public @ResponseBody
    Collection<BaseAnimal> search(@PathVariable String query) {
        return service.search(query);
    }
}
