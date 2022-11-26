package cx.catapult.animals.web;

import cx.catapult.animals.domain.Animal;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static cx.catapult.animals.web.AnimalFilter.filterAnimals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalFilterTest {


    //TODO: Convert to a parameterised test covering all cases
    @Test
    void testConditionsForFilter() {
        Collection<Animal> toFilter = List.of(
            new Animal("1233", "a", "nameA", "purple", "A detailed description"),
            new Animal("1233", "b", "nameA", "purple", "A detailed description"),
            new Animal("1233", "c", "nameB", "purple", "A detailed description"),
            new Animal("1233", "d", "nameD", "blue", "A detailed description")
        );

        var noFilters = filterAnimals(toFilter, "", "", "", "");
        assertEquals(4, noFilters.size());

        var typeFilter = filterAnimals(toFilter, "a", "", "", "");
        assertEquals(1, typeFilter.size());

        var nameFilter = filterAnimals(toFilter, "", "nameA", "", "");
        assertEquals(2, nameFilter.size());

        var colourFilter = filterAnimals(toFilter, "", "", "purple", "");
        assertEquals(3, colourFilter.size());

        var descriptionFilter = filterAnimals(toFilter, "", "", "", "A detailed description");
        assertEquals(4, descriptionFilter.size());

        var twoFlters = filterAnimals(toFilter, "", "", "purple", "A detailed description");
        assertEquals(3, twoFlters.size());

        var threeFilters = filterAnimals(toFilter, "", "nameA", "purple", "A detailed description");
        assertEquals(2, threeFilters.size());

        var fourFilters = filterAnimals(toFilter, "a", "nameA", "purple", "A detailed description");
        assertEquals(1, fourFilters.size());

        var typeAndDescriptionFilters = filterAnimals(toFilter, "a", "", "", "A detailed description");
        assertEquals(1, typeAndDescriptionFilters.size());

        var handleNull = filterAnimals(toFilter, null, "", "", "A detailed description");
        assertEquals(4, handleNull.size());
    }

}