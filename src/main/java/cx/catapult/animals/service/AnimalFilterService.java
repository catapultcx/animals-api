package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalFilterService {
    private final List<AnimalService> animalServices;

    public AnimalFilterService(List<AnimalService> animalServices) {
        this.animalServices = animalServices;
    }

    public Collection<Animal> filter(Optional<List<String>> types, Optional<List<String>> names, Optional<List<String>> colors, Optional<List<String>> descriptions) {
        return animalServices.stream()
                .filter(animalService ->
                                  types.isEmpty()
                                || types.get().isEmpty()
                                || types.get().contains(animalService.getAnimalType()))
                .parallel()
                .map(animalService -> animalService.filter(names, colors, descriptions))
                .flatMap(List::stream)
                .toList();
    }

}
