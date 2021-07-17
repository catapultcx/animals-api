package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.entity.CatEntity;
import cx.catapult.animals.entity.CatRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class CatDatabaseServiceTest {

    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final String ID = UUID.randomUUID().toString();

    @Mock
    private CatRepository catRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private CatsDatabaseService catDatabaseService;

    private CatEntity catEntity;

    private Cat cat;

    @BeforeEach
    public void setUp(){
        catEntity = new CatEntity();
        catEntity.setName(NAME);
        catEntity.setId(ID);
        catEntity.setDescription(DESCRIPTION);

        cat = new Cat();
        cat.setName(NAME);
        cat.setId(ID);
        cat.setDescription(DESCRIPTION);

        catDatabaseService = new CatsDatabaseService(catRepository, modelMapper);
    }

    @Test
    public void allShouldWork(){
        Collection<CatEntity> cats = Arrays.asList(catEntity);
        when(catRepository.findAll()).thenReturn(cats);
        Collection<Cat> all = catDatabaseService.all();

        assertThat(all.size()).isEqualTo(1);
        Cat result = all.iterator().next();

        assertThat(result.getName()).isEqualTo(NAME);
        assertThat(result.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(result.getId()).isEqualTo(ID);
    }

    @Test
    public void createShouldWork(){
        when(catRepository.save(any())).thenReturn(catEntity);
        Cat actual = catDatabaseService.create(cat);

        verify(catRepository).save(any());
        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getId()).isEqualTo(ID);
    }

    @Test
    // TODO actually change values
    public void updateShouldWork(){
        when(catRepository.save(any())).thenReturn(catEntity);
        Cat actual = catDatabaseService.update(ID, cat);

        verify(catRepository).save(any());
        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getId()).isEqualTo(ID);
    }

    @Test
    public void getShouldWork(){
        when(catRepository.findById(any())).thenReturn(Optional.of(catEntity));
        Cat actual = catDatabaseService.get(ID);

        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getId()).isEqualTo(ID);
    }

    @Test
    public void getShouldNotWork(){
        when(catRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> {
            catDatabaseService.get(ID);
        });
    }
}
