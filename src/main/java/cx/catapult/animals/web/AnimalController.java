package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.exceptions.UnsupportedAnimalTypeException;
import cx.catapult.animals.service.AnimalService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/2", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalController implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @GetMapping(value = "/{qualifier}", produces = "application/json")
    public @ResponseBody
    Collection<Animal> all(@PathVariable String qualifier) {
        return getAnimalService(qualifier).all();
    }

    @GetMapping(value = "/{qualifier}/{id}")
    public @ResponseBody
    Animal get(@PathVariable String qualifier, @PathVariable String id) {
        return getAnimalService(qualifier).get(id);
    }

    @PostMapping(value = "/{qualifier}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Animal create(@PathVariable String qualifier, @RequestBody Animal animal) {
        return getAnimalService(qualifier).create(animal);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public AnimalService getAnimalService(String qualifier) {
        try {
            return applicationContext.getBean(qualifier, AnimalService.class);
        } catch (BeansException e) {
            throw new UnsupportedAnimalTypeException(qualifier);
        }
    }
}
