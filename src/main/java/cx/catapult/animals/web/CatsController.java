package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.SearchRequest;
import cx.catapult.animals.service.CatSearchService;
import cx.catapult.animals.service.CatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/api/1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsController {

    @Autowired
    private CatsService service;

    @Autowired
    private CatSearchService catSearchService;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Cat> all() {
        return service.all();
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<Cat> search(@RequestParam(required = false) String name,
                     @RequestParam(required = false)  String desc) {
        return catSearchService.search(new SearchRequest(name, desc));
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

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@RequestBody Cat cat) {
        service.update(cat);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
