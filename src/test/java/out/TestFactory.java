package out;

import cx.catapult.animals.domain.Group;
import cx.catapult.animals.repository.AnimalRepository;
import cx.catapult.animals.service.AnimalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class TestFactory {

    @Scope("singleton")
    @Bean("dogs")
    public AnimalService getDogsService(AnimalRepository repository) {
        return new AnimalService("dog", Group.MAMMALS, repository);
    }


    @Scope("singleton")
    @Bean("owls")
    public AnimalService owlsService(AnimalRepository repository) {
        return new AnimalService("owl", Group.BIRD, repository);
    }


    @Scope("singleton")
    @Bean("parrots")
    public AnimalService parrotsService(AnimalRepository repository) {
        return new AnimalService("parrot", Group.BIRD, repository);
    }


    @Scope("singleton")
    @Bean("spiders")
    public AnimalService spidersService(AnimalRepository repository) {
        return new AnimalService("spider", Group.INVERTEBRATE, repository);
    }


    @Scope("singleton")
    @Bean("tunas")
    public AnimalService tunasService(AnimalRepository repository) {
        return new AnimalService("tuna", Group.FISH, repository);
    }


    @Scope("singleton")
    @Bean("salmons")
    public AnimalService salmonsService(AnimalRepository repository) {
        return new AnimalService("salmon", Group.FISH, repository);
    }


    @Scope("singleton")
    @Bean("frogs")
    public AnimalService frogsService(AnimalRepository repository) {
        return new AnimalService("frog", Group.AMPHIBIAN, repository);
    }


    @Scope("singleton")
    @Bean("iguanas")
    public AnimalService iguanasService(AnimalRepository repository) {
        return new AnimalService("iguana", Group.REPTILES, repository);
    }
}
