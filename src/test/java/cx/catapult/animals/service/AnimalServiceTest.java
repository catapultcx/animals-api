package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

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
}
