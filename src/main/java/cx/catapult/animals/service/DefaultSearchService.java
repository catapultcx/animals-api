package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Arachnid;
import cx.catapult.animals.domain.Cat;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
// TODO this implementation is much less than ideal
public class DefaultSearchService implements SearchService {
    private final cx.catapult.animals.service.Service<Cat> catService;

    private final cx.catapult.animals.service.Service<Arachnid> arachnidService;

    public DefaultSearchService(final cx.catapult.animals.service.Service<Cat> catService,
                                final cx.catapult.animals.service.Service<Arachnid> arachnidService) {
        this.catService = catService;
        this.arachnidService = arachnidService;
    }

    @Override
    public Optional<Animal> searchById(final String id) {
        Collection<Animal> animals = getAllAnimals();

        return animals.stream().filter(animal -> animal.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<Animal> searchByName(final String name) {
        Collection<Animal> animals = getAllAnimals();

        return animals.stream().filter(animal -> animal.getName().equals(name)).findFirst();
    }

    private Collection<Animal> getAllAnimals(){
        Collection<Animal> animals = this.catService.all().stream().map(cat -> (Animal)cat).collect(Collectors.toList());
        Collection<Animal> arachnids = this.arachnidService.all().stream().map(arachnid -> (Animal) arachnid).collect(Collectors.toList());

        animals.addAll(arachnids);

        return animals;
    }
}
