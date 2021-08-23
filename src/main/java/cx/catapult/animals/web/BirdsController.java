package cx.catapult.animals.web;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.service.BirdsService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping(path = "/api/1/birds", produces = APPLICATION_JSON_VALUE)
public class BirdsController {

  private final BirdsService birdsService;

  @GetMapping(value = EMPTY, produces = APPLICATION_JSON_VALUE)
  public @ResponseBody
  Collection<Bird> all() {
    return birdsService.all();
  }

  @GetMapping(value = "/{id}")
  public @ResponseBody
  Bird get(@PathVariable final String id) {
    return birdsService.get(id);
  }

  @PostMapping(value = EMPTY, consumes = APPLICATION_JSON_VALUE)
  @ResponseStatus(CREATED)
  public @ResponseBody
  Bird create(@RequestBody final Bird bird) {
    return birdsService.create(bird);
  }

  @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  public @ResponseBody
  Bird update(@PathVariable final String id, @RequestBody final Bird bird) {
    return birdsService.update(id, bird);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(OK)
  public @ResponseBody
  void delete(@PathVariable final String id) {
    birdsService.delete(id);
  }
}
