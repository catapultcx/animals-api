package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Group;
import cx.catapult.animals.factory.AnimalFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalServiceTest {
    private AnnotationConfigApplicationContext context;

    @BeforeEach
    public void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.scan(AnimalFactory.class.getPackage().getName());
        context.refresh();
    }

    @ParameterizedTest
    @CsvSource({
            "cats,Tom,cat,MAMMALS",
            "dogs,Pluto,dog,MAMMALS",
            "parrots,Beethoven,parrot,BIRD",
            "frogs,MrGreen,frog,AMPHIBIAN",
            "iguanas,Shifter,iguana,REPTILES",
            "tunas,Tuna,tuna,FISH",
            "salmons,Salmon,salmon,FISH",
            "spiders,MissHairy,spider,INVERTEBRATE"})
    public void createShouldWork(String qualifier, String name, String type, Group group) {
        AnimalService service = context.getBean(qualifier, AnimalService.class);
        Animal animal = new Animal();
        animal.setName(name);
        animal.setDescription(String.format("%s is my buddy", name));
        Animal actual = service.create(animal);
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(group);
        assertThat(actual.getType()).isEqualTo(type);
    }

    @ParameterizedTest
    @CsvSource({
            "cats,Tom,cat,MAMMALS,blue",
            "dogs,Pluto,dog,MAMMALS,blue",
            "parrots,Beethoven,parrot,BIRD,blue",
            "frogs,MrGreen,frog,AMPHIBIAN,blue",
            "iguanas,Shifter,iguana,REPTILES,blue",
            "tunas,Tuna,tuna,FISH,blue",
            "salmons,Salomon,salmon,FISH,blue",
            "spiders,MissHairy,spider,INVERTEBRATE,blue"})
    public void allShouldWork(String qualifier, String name, String type, Group group, String colorString) {
        Animal animal = new Animal(name, String.format("%s is my buddy", name), colorString);
        AnimalService service = context.getBean(qualifier, AnimalService.class);
        assertThat(service).isNotNull();
        service.create(animal);
        assertThat(service.all().size()).isEqualTo(1);
    }


    @ParameterizedTest
    @CsvSource({
            "cats,Tom,cat,MAMMALS,blue",
            "dogs,Pluto,dog,MAMMALS,blue",
            "parrots,Beethoven,parrot,BIRD,blue",
            "frogs,MrGreen,frog,AMPHIBIAN,blue",
            "iguanas,Shifter,iguana,REPTILES,blue",
            "tunas,Tuna,tuna,FISH,blue",
            "salmons,Salomon,salmon,FISH,blue",
            "spiders,MissHairy,spider,INVERTEBRATE,blue"})
    public void getShouldWork(String qualifier, String name, String type, Group group, String colorString) {
        Animal animal = new Animal(name, String.format("%s is my buddy", name), colorString);
        AnimalService service = context.getBean(qualifier, AnimalService.class);
        assertThat(service).isNotNull();
        service.create(animal);
        Animal actual = service.get(animal.getId());
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(animal.getGroup());
    }

    @ParameterizedTest
    @CsvSource({
            "cats,Tom,cat,MAMMALS,blue",
            "dogs,Pluto,dog,MAMMALS,blue",
            "parrots,Beethoven,parrot,BIRD,blue",
            "frogs,MrGreen,frog,AMPHIBIAN,blue",
            "iguanas,Shifter,iguana,REPTILES,blue",
            "tunas,Tuna,tuna,FISH,blue",
            "salmons,Salomon,salmon,FISH,blue",
            "spiders,MissHairy,spider,INVERTEBRATE,blue"})
    public void animalService_whenCalledForDelete_shouldWork(String qualifier, String name, String type, Group group, String colorString) {
        Animal animal = new Animal(name, String.format("%s is my buddy", name), colorString);
        AnimalService service = context.getBean(qualifier, AnimalService.class);
        assertThat(service).isNotNull();
        service.create(animal);
        boolean success = service.delete(animal.getId());
        assertThat(success).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"dogs,Pluto,dog,MAMMALS,blue"})
    public void animalService_whenCalledForDeleteForANotExistingItem_shouldFail(String qualifier, String name, String type, Group group, String colorString) {
        Animal animal = new Animal(name, String.format("%s is my buddy", name), colorString);
        AnimalService service = context.getBean(qualifier, AnimalService.class);
        assertThat(service).isNotNull();
        boolean success = service.delete(animal.getId());
        assertThat(success).isFalse();
    }


    @ParameterizedTest
    @CsvSource({
            "cats,Tom,cat,MAMMALS,blue",
            "dogs,Pluto,dog,MAMMALS,blue",
            "parrots,Beethoven,parrot,BIRD,blue",
            "frogs,MrGreen,frog,AMPHIBIAN,blue",
            "iguanas,Shifter,iguana,REPTILES,blue",
            "tunas,Tuna,tuna,FISH,blue",
            "salmons,Salomon,salmon,FISH,blue",
            "spiders,MissHairy,spider,INVERTEBRATE,blue"})
    public void animalService_whenCalledForUpdate_shouldUpdate(String qualifier, String name, String type, Group group, String colorString) {
        Animal animal = new Animal(name, String.format("%s is my buddy", name), colorString);
        AnimalService service = context.getBean(qualifier, AnimalService.class);
        assertThat(service).isNotNull();

        service.create(animal);
        Animal result = service.update(animal.getId(),animal);
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @CsvSource({
            "cats,Tom,cat,MAMMALS,blue",
            "dogs,Pluto,dog,MAMMALS,blue",
            "parrots,Beethoven,parrot,BIRD,blue",
            "frogs,MrGreen,frog,AMPHIBIAN,blue",
            "iguanas,Shifter,iguana,REPTILES,blue",
            "tunas,Tuna,tuna,FISH,blue",
            "salmons,Salomon,salmon,FISH,blue",
            "spiders,MissHairy,spider,INVERTEBRATE,blue"})
    public void animalService_whenCalledForANotExistingAnimal_shouldReturnNull(String qualifier, String name, String type, Group group, String colorString) {
        Animal animal = new Animal(name, String.format("%s is my buddy", name), colorString);
        String notExist = "not_exist";
        animal.setId(notExist);
        AnimalService service = context.getBean(qualifier, AnimalService.class);
        assertThat(service).isNotNull();

        Animal result = service.update(notExist,animal);
        assertThat(result).isNull();
    }
}
