package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalFilterService {
    private final ApplicationContext applicationContext;

    public AnimalFilterService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Collection<Animal> filter(Optional<List<String>> types, Optional<List<String>> names, Optional<List<String>> colors, Optional<List<String>> descriptions) {
        Collection<AnimalService> animalServices = applicationContext.getBeansOfType(AnimalService.class).values();
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
