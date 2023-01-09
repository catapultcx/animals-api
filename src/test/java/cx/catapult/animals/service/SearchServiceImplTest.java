package cx.catapult.animals.service;

import cx.catapult.animals.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class SearchServiceImplTest {

    private final SearchServiceImpl searchService = new SearchServiceImpl();

    private final AnimalService<? extends BaseAnimal> animalService = Mockito.mock(AnimalService.class);

    @BeforeEach
    public void setUp() {
        searchService.setAnimalService(animalService);
    }

    @Test
    @DisplayName("Filters the data based on filter criteria")
    public void searchesDataBasedOnFilterCriteria() {
        Cat catWithNoColor = new Cat("Tom", "Friend of Jerry");
        Cat catWithColor = new Cat("Tom", "Friend of Jerry", new Colour[]{Colour.WHITE, Colour.BLACK});
        Cat catWithSingleColor = new Cat("Tom", "Friend of Jerry", new Colour[]{Colour.BLACK});
        Fish fish = new Fish("Tom", "Friend of Jerry", new Colour[]{Colour.BLACK});


        when(animalService.all()).thenReturn(Arrays.asList(catWithNoColor, catWithColor, catWithSingleColor, fish));

        FilterCriteria fc = new FilterCriteria(Group.MAMMALS, "Tom", Colour.BLACK, "Friend of Jerry");
        List<? extends BaseAnimal> animals = searchService.searchAnimals(fc);
        assertThat(animals.size()).isEqualTo(2);
        assertThat(animals.get(0)).isEqualTo(catWithColor);
        assertThat(animals.get(1)).isEqualTo(catWithSingleColor);
    }

    @Test
    @DisplayName("Returns empty list if filter criteria is null")
    public void returnsEmptyListIfFilterCriteriaIsNull() {
        assertThat(searchService.searchAnimals(null)).isEmpty();
    }
}