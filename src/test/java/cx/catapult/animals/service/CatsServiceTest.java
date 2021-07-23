package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Group;
import cx.catapult.animals.repository.AnimalRepository;
import cx.catapult.animals.repository.entity.AnimalEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatsServiceTest {

    CatsService service;
    Cat cat;

    @Mock
    AnimalRepository animalRepository;

    @BeforeEach
    void setUp() {
        cat = new Cat("Tom", "Bob cat");
        service = new CatsService(animalRepository);
    }

    @Test
    void createShouldWork() {
        AnimalEntity animalEntity = getAnimalEntity();
        when(animalRepository.save(any(AnimalEntity.class))).thenReturn(animalEntity);

        Cat actual = service.create(cat);

        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
        assertThat(actual.getId()).isEqualTo("1");
        verify(animalRepository,times(1)).save(any(AnimalEntity.class));
    }

    @Test
    void allShouldWork() {
        AnimalEntity animalEntity = getAnimalEntity();
        when(animalRepository.findByAnimalType("CAT")).thenReturn(asList(animalEntity));

        assertThat(service.all().size()).isEqualTo(1);
        verify(animalRepository,times(1)).findByAnimalType(eq("CAT"));
    }

    @Test
    void getShouldWork() {
        AnimalEntity animalEntity = getAnimalEntity();
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animalEntity));

        Cat actual = service.get("1");
        
        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
        assertThat(actual.getId()).isEqualTo("1");
        verify(animalRepository,times(1)).findById(eq(1L));
    }

    @Test
    void updateShouldWork() {
        AnimalEntity animalEntity = getAnimalEntity();
        when(animalRepository.findById(1L)).thenReturn(Optional.ofNullable(animalEntity));
        when(animalRepository.save(any(AnimalEntity.class))).thenReturn(any(AnimalEntity.class));
        cat.setName("Test");
        cat.setId("1");

        service.update(cat);

        verify(animalRepository,times(1)).findById(eq(1L));
        animalEntity.setName("Test");
        verify(animalRepository,times(1)).save(eq(animalEntity));
    }

    private AnimalEntity getAnimalEntity() {
        return AnimalEntity.builder()
                .name("Tom")
                .animalType("CAT")
                .description("Bob cat")
                .group(Group.MAMMALS.name())
                .id(1L)
                .build();
    }
}
