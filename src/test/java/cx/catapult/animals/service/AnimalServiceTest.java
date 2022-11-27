package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.AnimalFactory;
import cx.catapult.animals.domain.AnimalType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnimalServiceTest {

    private final AnimalService service = new AnimalService();
    private final Animal aCat = AnimalFactory.aCat() ;

    @Test
    public void createShouldWork() throws Exception {
        var actual = service.create(aCat);
        assertThat(actual).isEqualTo(aCat);
        assertThat(actual.getName()).isEqualTo(aCat.getName());
        assertThat(actual.getDescription()).isEqualTo(aCat.getDescription());
        assertThat(actual.getType()).isEqualTo(aCat.getType());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(aCat);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(aCat);
        var actual = service.get(aCat.getId());
        assertThat(actual).isEqualTo(aCat);
        assertThat(actual.getName()).isEqualTo(aCat.getName());
        assertThat(actual.getDescription()).isEqualTo(aCat.getDescription());
        assertThat(actual.getType()).isEqualTo(aCat.getType());
        assertThat(actual.getColour()).isEqualTo(aCat.getColour());
    }

    @Test
    public void deleteShouldWorkIfAnimialWithIdExist() {
        service.create(aCat);
        var id = aCat.getId();

        var deleted = service.delete(id);

        assertThat(deleted.getId()).isEqualTo(id);
        assertNull(service.get(id));
    }

    @Test
    public void updateShouldWorkIfAnimialWithIdExist() {
        service.create(aCat);
        var id = aCat.getId();

        var animalToUpdate = new Animal("updated", "updated desc", "Red", AnimalType.MAMMALS);

        var updated = service.update(id, animalToUpdate);

        assertThat(updated.getId()).isEqualTo(id);
        assertThat(updated.getName()).isEqualTo(animalToUpdate.getName());
        assertThat(updated.getDescription()).isEqualTo(animalToUpdate.getDescription());
        assertThat(updated.getColour()).isEqualTo(animalToUpdate.getColour());
    }

    @Test
    public void filter_NameShouldBeConsideredInSearchTermMatching() {
        service.create(new Animal("cat", "cat desc", "orange", AnimalType.MAMMALS));
        service.create(new Animal("eagle", "eagle desc", "white", AnimalType.BIRD));

        var results = service.filter("cat");

        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("cat");
    }

    @Test
    public void filter_descriptionShouldBeConsideredInSearchTermMatching() {
        service.create(new Animal("cat", "cat desc", "orange", AnimalType.MAMMALS));
        service.create(new Animal("eagle", "eagle desc", "white", AnimalType.BIRD));
        service.create(new Animal("snake", "long snake", "white", AnimalType.REPTILES));

        var results = service.filter("desc");

        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactlyInAnyOrder("cat", "eagle");
    }

    @Test
    public void filter_ColourShouldBeConsideredInSearchTermMatching() {
        service.create(new Animal("cat", "cat desc", "orange", AnimalType.MAMMALS));
        service.create(new Animal("eagle", "eagle desc", "white", AnimalType.BIRD));
        service.create(new Animal("snake", "long snake", "white", AnimalType.REPTILES));

        var results = service.filter("whi");

        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactlyInAnyOrder("snake", "eagle");
    }

    @Test
    public void filter_TypeShouldBeConsideredInSearchTermMatching() {
        service.create(new Animal("cat", "cat desc", "orange", AnimalType.MAMMALS));
        service.create(new Animal("eagle", "eagle desc", "white", AnimalType.BIRD));
        service.create(new Animal("snake", "long snake", "white", AnimalType.REPTILES));

        var results = service.filter("bird");

        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("eagle");
    }

    @ParameterizedTest
    @MethodSource("filteringData")
    public void filterShouldReturnAllAnimalsThatContainGivenSearchTermInAnyFields(String searchTerm, int expectedCount, List<String> expectedNames) {
        service.create(new Animal("cat", "cat desc", "orange", AnimalType.MAMMALS));
        service.create(new Animal("eagle", "eagle eats fish", "white", AnimalType.BIRD));
        service.create(new Animal("Jelly fish", "small fish", "orange", AnimalType.FISH));
        service.create(new Animal("snake", "long snake", "white", AnimalType.REPTILES));
        service.create(new Animal("Dolphin", "large fish", "orange", AnimalType.FISH));
        service.create(new Animal("bird desc", "large bird with white wings", "black", AnimalType.BIRD));

        var results = service.filter(searchTerm);

        assertThat(results.size()).isEqualTo(expectedCount);
        assertThat(results).extracting("name").containsExactlyInAnyOrder(expectedNames.toArray());
    }

    private static Stream<Arguments> filteringData() {
        return Stream.of(
                Arguments.of("desc", 2, List.of("cat", "bird desc")),
                Arguments.of("white", 3, List.of("eagle", "snake", "bird desc")),
                Arguments.of("fish", 3, List.of("eagle", "Jelly fish", "Dolphin")),
                Arguments.of("bird", 2, List.of("bird desc", "eagle"))
        );
    }
}
