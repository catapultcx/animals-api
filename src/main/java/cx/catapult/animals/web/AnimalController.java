package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.service.AnimalService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static cx.catapult.animals.web.AnimalFilter.filterAnimals;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalController {

    private final AnimalService service;

    @Autowired
    public AnimalController(AnimalService service) {
        this.service = service;
    }

    //TODO: Implement validation

    @GetMapping(value = "/owners/{ownerId}/animals", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Collection<Animal> all(@PathVariable String ownerId,
                                  @RequestParam(value = "type", required=false) String type,
                                  @RequestParam(value = "name", required=false) String name,
                                  @RequestParam(value = "colour", required=false) String colour,
                                  @RequestParam(value = "description", required=false) String description
    ) {
        final Collection<Animal> allAnimalsForOwner = service.getAllAnimalsForOwner(ownerId);
        return filterAnimals(allAnimalsForOwner, type, name, colour, description);
    }

    @GetMapping(value = "/owners/{ownerId}/animals/{animalId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Animal get(@PathVariable String ownerId, @PathVariable String animalId) {
        return service.getAnimalForOwner(ownerId, animalId);
    }

    @PostMapping(value = "/owners/{ownerId}/animals", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Animal create(@RequestBody Animal animal, @PathVariable String ownerId) {
        return service.createAnimalForOwner(ownerId, animal);
    }

    @DeleteMapping(value = "/owners/{ownerId}/animals/{animalId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String ownerId, @PathVariable String animalId) {
        service.removeAnimalForOwner(ownerId, animalId);
    }

    @PutMapping(value = "/owners/{ownerId}/animals/{animalId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Animal update(@PathVariable String ownerId, @PathVariable String animalId, @RequestBody Animal animal) {
        return service.updateAnimalForOwner(ownerId, animal);
    }

}
