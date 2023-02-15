package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.factory.AnimalFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@Import({AnimalFactory.class, AnimalFilterService.class})
class AnimalFilterServiceTest {

    private static AnimalFilterService filterService;
    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(AnimalFactory.class, AnimalFilterService.class);
        context.refresh();
        filterService = context.getBean(AnimalFilterService.class);
        assertThat(filterService).isNotNull();
        createTestData();
    }

    private static void createTestData() {
        Map<String, AnimalService> services = context.getBeansOfType(AnimalService.class);
        Animal[] animals = {
         new Animal("Kate", "A thing with some props", "black"),
         new Animal("Mate", "Name some desc", "blue"),
         new Animal("Sade", "A desc with props", "gray"),
         new Animal("Manu", "A desc with props", "gray"),
        };
        services.values().forEach(animalService -> {
            Animal[] clone = animals.clone();
            Arrays.stream(clone).forEach(animalService::create);
        });
    }


    @ParameterizedTest
    @CsvSource({
            ",,,,36",
            "iguana,,,,4",
            "iguana|dog,,,,8",
            ",te,,,18",
            ",,gray,,18",
            ",,,some,18",
            "parrot|dog,te,,,4",
            "spider|dog,te|Manu,,,6",
            "parrot|dog,te|Manu,black,,2",
            "parrot,te|Manu,black|gray,,2",
            "iguana,te|Manu,black|gray,some,1",
            "parrot,te|Manu,,some,2",
            "parrot,te|Manu,,desc,2",
            "parrot,te|Manu,,desc|props,3",
            ",te|Manu,,desc|props,27",
            ",te|Manu,gray,desc|props,9",
    })
    public void filterService_whenProvidedParameter_shouldFilter(
            String types,
            String names,
            String colors,
            String descriptions,
            int result
    ) {
        Collection<Animal> animals = filterService.filter(
                Optional.ofNullable(types).map(it -> Arrays.asList(it.split("\\|"))),
                Optional.ofNullable(names).map(it -> Arrays.asList(it.split("\\|"))),
                Optional.ofNullable(colors).map(it -> Arrays.asList(it.split("\\|"))),
                Optional.ofNullable(descriptions).map(it -> Arrays.asList(it.split("\\|")))
        );
        assertThat(animals.size()).isEqualTo(result);
    }




}
