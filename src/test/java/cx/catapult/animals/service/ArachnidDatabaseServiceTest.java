package cx.catapult.animals.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cx.catapult.animals.domain.Arachnid;
import cx.catapult.animals.entity.ArachnidEntity;
import cx.catapult.animals.entity.ArachnidRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ArachnidDatabaseServiceTest {

    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final String ID = UUID.randomUUID().toString();

    @Mock
    private ArachnidRepository arachnidRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private ArachnidDatabaseService arachnidDatabaseService;

    private ArachnidEntity arachnidEntity;

    private Arachnid arachnid;

    @BeforeEach
    public void setUp(){
        arachnidEntity = new ArachnidEntity();
        arachnidEntity.setName(NAME);
        arachnidEntity.setId(ID);
        arachnidEntity.setDescription(DESCRIPTION);

        arachnid = new Arachnid();
        arachnid.setName(NAME);
        arachnid.setId(ID);
        arachnid.setDescription(DESCRIPTION);

        arachnidDatabaseService = new ArachnidDatabaseService(arachnidRepository, modelMapper);
    }

    @Test
    public void allShouldWork(){
        Collection<ArachnidEntity> arachnids = Arrays.asList(arachnidEntity);
        when(arachnidRepository.findAll()).thenReturn(arachnids);
        Collection<Arachnid> all = arachnidDatabaseService.all();

        assertThat(all.size()).isEqualTo(1);
        Arachnid result = all.iterator().next();

        assertThat(result.getName()).isEqualTo(NAME);
        assertThat(result.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(result.getId()).isEqualTo(ID);
    }

    @Test
    public void createShouldWork(){
        when(arachnidRepository.save(any())).thenReturn(arachnidEntity);
        Arachnid actual = arachnidDatabaseService.create(arachnid);

        verify(arachnidRepository).save(any());
        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getId()).isEqualTo(ID);
    }

    @Test
    // TODO actually change values
    public void updateShouldWork(){
        when(arachnidRepository.save(any())).thenReturn(arachnidEntity);
        Arachnid actual = arachnidDatabaseService.update(ID, arachnid);

        verify(arachnidRepository).save(any());
        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getId()).isEqualTo(ID);
    }

    @Test
    public void getShouldWork(){
        when(arachnidRepository.findById(any())).thenReturn(Optional.of(arachnidEntity));
        Arachnid actual = arachnidDatabaseService.get(ID);

        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getId()).isEqualTo(ID);
    }

    @Test
    public void getShouldNotWork(){
        when(arachnidRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> {
            arachnidDatabaseService.get(ID);
        });
    }

    @Test
    public void deleteShouldWork(){
        when(arachnidRepository.findById(any())).thenReturn(Optional.of(arachnidEntity));
        Arachnid actual = arachnidDatabaseService.delete(ID);

        verify(arachnidRepository).delete(any());

        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getId()).isEqualTo(ID);
    }

    @Test
    public void deleteShouldNotWork(){
        when(arachnidRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> {
            arachnidDatabaseService.delete(ID);
        });
    }

}
