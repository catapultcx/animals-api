package cx.catapult.animals.service;

import cx.catapult.animals.configuration.AnimalFactoryConfiguration;
import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableConfigurationProperties(AnimalFactoryConfiguration.class)
@Import(AnimalFilterService.class)
class AnimalFilterServiceTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    AnimalRepository animalRepository;
    private AnimalFilterService filterService;

    @BeforeEach
    public void setUp() {
        filterService = context.getBean(AnimalFilterService.class);
        assertThat(filterService).isNotNull();
        createTestData();
    }

    private void createTestData() {
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
            "cat,,,,4",
            "iguana|dog,,,,8",
            ",Kate|Mate,,,18",
            ",,gray,,18",
            ",,,A thing with some props|Name some desc,18",
            "parrot|dog,Kate|Mate,,,4",
            "spider|dog,Kate|Mate|Manu,,,6",
            "parrot|dog,Kate|Mate|Manu,black,,2",
            "parrot,Kate|Mate|Manu,black|gray,,2",
            "iguana,Kate|Mate|Manu,black|gray,A thing with some props|Name some desc,1",
            "parrot,Kate|Mate|Manu,,A thing with some props|Name some desc,2",
            "parrot,Kate|Mate|Manu,,Name some desc|A desc with props,2",
            "parrot,Kate|Mate|Manu,,Name some desc|A desc with props|A thing with some props,3",
            ",Kate|Mate|Manu,,Name some desc|A desc with props|A thing with some props,27",
            ",Kate|Mate|Manu,gray,Name some desc|A desc with props|A thing with some props,9",
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
