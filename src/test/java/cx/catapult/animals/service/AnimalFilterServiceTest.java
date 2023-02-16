package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.configuration.AnimalFactoryConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import out.TestFactory;

import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnimalFilterServiceTest {

    private static AnimalFilterService filterService;
    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(TestFactory.class, AnimalFactoryConfiguration.class, AnimalFilterService.class);
        context.refresh();
        filterService = context.getBean(AnimalFilterService.class);
        assertThat(filterService).isNotNull();
        createTestData();
    }

    private static void createTestData() {
        Map<String, AnimalService> services = context.getBeansOfType(AnimalService.class);
        List<Animal> animals = List.of(
                new Animal("Kate", "A thing with some props", "black"),
                new Animal("Mate", "Name some desc", "blue"),
                new Animal("Sade", "A desc with props", "gray"),
                new Animal("Manu", "A desc with props", "gray")
        );
        services.values().forEach(animalService -> animals.forEach(animalService::create));
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
        Function<String, Optional<List<String>>> filterExpression =
                (String ex) -> Optional.ofNullable(ex).map(it -> Arrays.asList(it.split("\\|")));

        Collection<Animal> animals = filterService.filter(
                filterExpression.apply(types),
                filterExpression.apply(names),
                filterExpression.apply(colors),
                filterExpression.apply(descriptions)
        );
        assertThat(animals.size()).isEqualTo(result);
    }




}
