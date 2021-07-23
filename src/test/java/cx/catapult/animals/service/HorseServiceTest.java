package cx.catapult.animals.service;

import cx.catapult.animals.domain.Group;
import cx.catapult.animals.domain.Horse;
import cx.catapult.animals.exception.AnimalNotFoundException;
import cx.catapult.animals.repository.AnimalRepository;
import cx.catapult.animals.repository.entity.AnimalEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HorseServiceTest {

    HorseService service ;
    Horse horse;

    @Mock
    AnimalRepository animalRepository;

    @BeforeEach
    void setUp() {
        horse = new Horse("Spirit", "Black Stallion");
        service = new HorseService(animalRepository);
    }

    @Test
    void createShouldWork() {
        AnimalEntity animalEntity = getAnimalEntity();
        when(animalRepository.save(any(AnimalEntity.class))).thenReturn(animalEntity);
        
        Horse actual = service.create(horse);

        assertThat(actual.getName()).isEqualTo(horse.getName());
        assertThat(actual.getDescription()).isEqualTo(horse.getDescription());
        assertThat(actual.getGroup()).isEqualTo(horse.getGroup());
        assertThat(actual.getId()).isEqualTo("1");
        verify(animalRepository,times(1)).save(any(AnimalEntity.class));
    }

    @Test
    public void allShouldWork() {
        AnimalEntity animalEntity = getAnimalEntity();
        when(animalRepository.findByAnimalType("HORSE")).thenReturn(asList(animalEntity));

        assertThat(service.all().size()).isEqualTo(1);
        verify(animalRepository,times(1)).findByAnimalType(eq("HORSE"));
    }

    @Test
    public void getShouldWork() {
        AnimalEntity animalEntity = getAnimalEntity();
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animalEntity));

        Horse actual = service.get("1");

        assertThat(actual.getName()).isEqualTo(horse.getName());
        assertThat(actual.getDescription()).isEqualTo(horse.getDescription());
        assertThat(actual.getGroup()).isEqualTo(horse.getGroup());
        assertThat(actual.getId()).isEqualTo("1");
        verify(animalRepository,times(1)).findById(eq(1L));
    }

    @Test
    void updateShouldWork() {
        AnimalEntity animalEntity = getAnimalEntity();
        when(animalRepository.findById(1L)).thenReturn(Optional.ofNullable(animalEntity));
        when(animalRepository.save(any(AnimalEntity.class))).thenReturn(any(AnimalEntity.class));
        horse.setName("Test");
        horse.setId("1");

        service.update(horse);

        verify(animalRepository,times(1)).findById(eq(1L));
        animalEntity.setName("Test");
        verify(animalRepository,times(1)).save(eq(animalEntity));
    }

    @Test
    public void deleteShouldWork() {
        service.delete("1");

        verify(animalRepository,times(1)).deleteById(eq(1L));
    }

//    @Test
//    public void delete_shouldThrowExceptionWhenIdNotFound() {
//        when(animalRepository.deleteById(0L)).thenThrow(new EmptyResultDataAccessException());
//        service.delete("1");
//
//        verify(animalRepository,times(1)).deleteById(eq(1L));
//    }

    private AnimalEntity getAnimalEntity() {
        return AnimalEntity.builder()
                .name("Spirit")
                .animalType("HORSE")
                .description("Black Stallion")
                .group(Group.MAMMALS.name())
                .id(1L)
                .build();
    }
}
