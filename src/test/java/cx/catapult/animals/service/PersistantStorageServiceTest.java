package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cx.catapult.animals.data.entity.AnimalEntity;
import cx.catapult.animals.data.repository.AnimalRepository;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Group;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PersistantStorageServiceTest {
    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private PersistantStorageService<BaseAnimal> underTest;

    @Captor
    private ArgumentCaptor<AnimalEntity> animalEntityArgumentCaptor;

    private final BaseAnimal animal = new BaseAnimal("name", "description", Group.BIRD);

    @Test
    public void shouldCreate() {
        final AnimalEntity entity = entity();
        when(animalRepository.save(animalEntityArgumentCaptor.capture())).thenReturn(entity);
        final BaseAnimal baseAnimal = underTest.create(animal);
        verify(animalRepository).save(animalEntityArgumentCaptor.getValue());
        assertThat(baseAnimal).isNotNull();
    }

    @Test
    public void shouldReturnAll() {
        when(animalRepository.findAll()).thenReturn(Arrays.asList(entity()));
        final Collection<BaseAnimal> all = underTest.all();
        assertThat(all).hasSize(1);
    }

    @Test
    public void shouldGet() {
        when(animalRepository.findOneByAnimalId(any())).thenReturn(entity());
        underTest.get("123");
        verify(animalRepository).findOneByAnimalId("123");
    }

    @Test
    public void shouldDelete() {
        doNothing().when(animalRepository).deleteByAnimalId(any());
        underTest.delete("123");
        verify(animalRepository).deleteByAnimalId("123");
    }

    @Test
    public void shouldUpdate() {
        when(animalRepository.findOneByAnimalId(any())).thenReturn(entity());
        when(animalRepository.save(any())).thenReturn(entity());
        underTest.update("123", new BaseAnimal("test", "description", Group.BIRD));
        verify(animalRepository).findOneByAnimalId("123");
        verify(animalRepository).save(animalEntityArgumentCaptor.capture());
        final AnimalEntity captured = animalEntityArgumentCaptor.getValue();
        assertThat(captured).isNotNull();
        assertThat(captured.getDescription()).isEqualTo("description");
        assertThat(captured.getName()).isEqualTo("test");
        assertThat(captured.getGroup()).isEqualTo("BIRD");
    }

    private AnimalEntity entity() {
        final AnimalEntity entity = new AnimalEntity();
        entity.setGroup("BIRD");
        return entity;
    }
}
