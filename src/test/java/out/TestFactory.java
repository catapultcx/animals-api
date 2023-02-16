package out;

import cx.catapult.animals.domain.Group;
import cx.catapult.animals.service.AnimalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class TestFactory {

    @Scope("singleton")
    @Bean("dogs")
    public AnimalService getDogsService() {
        return new AnimalService("dog", Group.MAMMALS);
    }


    @Scope("singleton")
    @Bean("owls")
    public AnimalService owlsService() {
        return new AnimalService("owl", Group.BIRD);
    }


    @Scope("singleton")
    @Bean("parrots")
    public AnimalService parrotsService() {
        return new AnimalService("parrot", Group.BIRD);
    }


    @Scope("singleton")
    @Bean("spiders")
    public AnimalService spidersService() {
        return new AnimalService("spider", Group.INVERTEBRATE);
    }


    @Scope("singleton")
    @Bean("tunas")
    public AnimalService tunasService() {
        return new AnimalService("tuna", Group.FISH);
    }


    @Scope("singleton")
    @Bean("salmons")
    public AnimalService salmonsService() {
        return new AnimalService("salmon", Group.FISH);
    }


    @Scope("singleton")
    @Bean("frogs")
    public AnimalService frogsService() {
        return new AnimalService("frog", Group.AMPHIBIAN);
    }


    @Scope("singleton")
    @Bean("iguanas")
    public AnimalService iguanasService() {
        return new AnimalService("iguana", Group.REPTILES);
    }
}
