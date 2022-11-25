package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Iguana;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AnimalServiceTest {

    private AnimalService service;

    @BeforeEach
    void setUp() {
        service = new AnimalService();
    }

    @ParameterizedTest
    @ArgumentsSource(AnimalSubtypeProvider.class)
    void allShouldWork(BaseAnimal animal) throws Exception {
        service.create(animal);
        assertThat(service.all()).hasSize(1);
    }

    @ParameterizedTest
    @ArgumentsSource(AnimalSubtypeProvider.class)
    void getShouldWork(BaseAnimal animal) throws Exception {
        service.create(animal);
        BaseAnimal actual = service.get(animal.getId());
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getClassification()).isEqualTo(animal.getClassification());
    }

    @ParameterizedTest
    @ArgumentsSource(AnimalSubtypeProvider.class)
    void testCreateWorks(BaseAnimal animal) throws Exception {
        service.create(animal);
        BaseAnimal actual = service.get(animal.getId());
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getClassification()).isEqualTo(animal.getClassification());
    }

    private static class AnimalSubtypeProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    new Cat("name", "description", "colour"),
                    new Iguana("name", "description", "colour"))
                .map(Arguments::of);
        }
    }

}
