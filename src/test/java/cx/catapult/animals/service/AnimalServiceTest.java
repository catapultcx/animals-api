package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;
    private AnimalService service;

    @BeforeEach
    void setUp() {
        service = new AnimalServiceImpl(animalRepository);
    }

    @Test
    void allShouldWork() {
        Mockito.when(animalRepository.getAllAnimalsForOwner(any()))
            .thenReturn(List.of(
                new Animal("1233", "type", "name", "purple", "A detailed description")
            ));
        var allAnimalsForOwner = service.getAllAnimalsForOwner("1234");

        var expected = List.of(new Animal("1233", "type", "name", "purple", "A detailed description"));
        assertThat(allAnimalsForOwner).hasSize(1);
        assertEquals(expected, allAnimalsForOwner);
        Mockito.verify(animalRepository, times(1)).getAllAnimalsForOwner(eq("1234"));
    }

    @Test
    void getShouldWork() {
        Mockito.when(animalRepository.getAnimalForOwner(any(), eq("1233")))
            .thenReturn(
                new Animal("1233", "type", "name", "purple", "A detailed description")
            );

        var animalForOwner = service.getAnimalForOwner("1234", "1233");

        var expected = new Animal("1233", "type", "name", "purple", "A detailed description");
        assertEquals(expected, animalForOwner);
        Mockito.verify(animalRepository, times(1)).getAnimalForOwner(eq("1234"), eq("1233"));

    }

    @Test
    void testCreateWorks() {
        Mockito.when(animalRepository.createAnimalForOwner(any(), any()))
            .thenReturn(
                new Animal("1233", "type", "name", "purple", "A detailed description")
            );

        var createdAnimal = service.createAnimalForOwner("1234",
            new Animal("1233", "type", "name", "purple", "A detailed description"));
        var expected = new Animal("1233", "type", "name", "purple", "A detailed description");

        assertEquals(expected, createdAnimal);
        Mockito.verify(animalRepository, times(1)).createAnimalForOwner(eq("1234"), any());
    }

    @Test
    void testDeleteWorks() {
        Mockito.when(animalRepository.createAnimalForOwner(any(), any()))
            .thenReturn(
                new Animal("1233", "type", "name", "purple", "A detailed description")
            );

        var createdAnimal = service.createAnimalForOwner("1234",
            new Animal("1233", "type", "name", "purple", "A detailed description"));
        var expected = new Animal("1233", "type", "name", "purple", "A detailed description");

        assertEquals(expected, createdAnimal);

        Mockito.doNothing().when(animalRepository).removeAnimalForOwner(any(),any());

        Mockito.when(animalRepository.getAllAnimalsForOwner(any()))
            .thenReturn(
                Collections.emptyList()
            );
        animalRepository.removeAnimalForOwner("1234", "1233");
        var allAnimalsForOwner = service.getAllAnimalsForOwner("1234");
        assertThat(allAnimalsForOwner).isEmpty();

        Mockito.verify(animalRepository, times(1)).removeAnimalForOwner(any(), any());
    }


}
