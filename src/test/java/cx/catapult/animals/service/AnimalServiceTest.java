package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @InjectMocks
    AnimalService service;

    Animal animal;

    @BeforeEach
    public void setUp() {
        animal = new Animal("Jerry", "Mouse Cat", "Grey", "Mouse");
    }

    @Test
    public void createShouldWork() {
        //Act
        Animal actual = service.create(animal);
        //Assert
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(animal.getGroup());
    }

    @Test
    public void allShouldWork() {
        service.create(animal);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        //Arrange
        service.create(animal);
        //Act
        Animal actual = service.get(animal.getId());
        //Assert
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(animal.getGroup());
    }

    @Test
    public void updateShouldWork() {
        //Arrange
        Animal createdAnimal =  service.create(animal);
        createdAnimal.setName("Tom");
        createdAnimal.setDescription("Fluffy Cat");
        createdAnimal.setColour("Black");
        createdAnimal.setColour("Cat");
        //Act
        Animal actual = service.update(createdAnimal);
        //Assert
        assertThat(actual).isEqualTo(createdAnimal);
        assertThat(actual.getName()).isEqualTo(createdAnimal.getName());
        assertThat(actual.getDescription()).isEqualTo(createdAnimal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(createdAnimal.getGroup());
    }

    @Test
    public void deleteShouldWork() {
        Animal actual = service.create(animal);
        assertNotNull(service.get(actual.getId()));

        service.delete(actual.getId());
        assertNull(service.get(actual.getId()));
    }

    @Test
    public void filterByNameShouldWork() {
        service.create(animal);
        List<Animal> actual = service.filter("Jerry", null, null, null);
        assertEquals(1, actual.size());
        assertThat(actual.get(0).getName()).isEqualTo("Jerry");
    }

    @Test
    public void filterByNameShouldReturnEmptyList() {
        service.create(animal);
        List<Animal> actual = service.filter("Tom", null, null, null);
        assertEquals(0, actual.size());
        assertTrue(actual.isEmpty());
    }

    @Test
    public void filterByNameAndColourShouldWork() {
        service.create(animal);
        List<Animal> actual = service.filter("Jerry", null, "Grey", null);
        assertEquals(1, actual.size());
        assertThat(actual.get(0).getType()).isEqualTo("Mouse");
    }
}
