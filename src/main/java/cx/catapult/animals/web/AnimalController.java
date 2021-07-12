package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Arachnid;
import cx.catapult.animals.service.ArachnidsService;
import cx.catapult.animals.service.BaseService;
import cx.catapult.animals.service.CatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/1/{animal}", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalController {

  private BaseService<Animal> service;

  @Autowired
  private CatsService catsService;

  @Autowired
  private ArachnidsService arachnidsService;

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public @ResponseBody
  Animal create(@RequestBody Arachnid arachnid, @PathVariable String animal) {
    return getAnimalService(animal).create(arachnid);
  }

  private BaseService getAnimalService(String animalPath) {
    if (animalPath.equals("cats")) {
      return catsService;
    }
    return arachnidsService;
  }

}
