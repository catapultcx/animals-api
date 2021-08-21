package cx.catapult.animals.service;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.repository.BirdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BirdsServiceTest {

    @Mock
    BirdRepository birdRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    BirdsService service;

    Bird bird = new Bird("Tom", "Bob bird");

    @Test
    public void createShouldWork()  {

        Bird thisBird = new Bird();

        Mockito.when(birdRepository.save(thisBird)).thenReturn(thisBird);

        thisBird.setName("Jerry");
        thisBird.setDescription("Mouse Bird");
        Bird actual = service.create(thisBird);
        assertThat(actual).isEqualTo(thisBird);
        assertThat(actual.getName()).isEqualTo(thisBird.getName());
        assertThat(actual.getDescription()).isEqualTo(thisBird.getDescription());
        assertThat(actual.getAnimalGroup()).isEqualTo(thisBird.getAnimalGroup());
    }

    @Test
    public void allShouldWork() {
        service.create(bird);

        ArrayList<Bird> allBirds = new ArrayList<>();
        allBirds.add(bird);
        Mockito.when(birdRepository.findAll()).thenReturn(allBirds);

        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        Mockito.when(birdRepository.save(bird)).thenReturn(bird);

        service.create(bird);
        Mockito.when(birdRepository.findById(bird.getId())).thenReturn(Optional.of(bird));
        Bird actual = service.get(bird.getId());
        assertThat(actual).isEqualTo(bird);
        assertThat(actual.getName()).isEqualTo(bird.getName());
        assertThat(actual.getDescription()).isEqualTo(bird.getDescription());
        assertThat(actual.getAnimalGroup()).isEqualTo(bird.getAnimalGroup());
    }

    @Test
    public void deleteShouldWork() {
        Mockito.when(birdRepository.save(bird)).thenReturn(bird);
        service.create(bird);

        ArrayList<Bird> allBirds = new ArrayList<>();
        allBirds.add(bird);
        Mockito.when(birdRepository.findAll()).thenReturn(allBirds);

        int size  = service.all().size();
        assertThat(size).isEqualTo(1);

        allBirds.clear();

        service.delete(bird.getId());
        int size2  = service.all().size();
        assertThat(size2).isEqualTo(0);
    }

    @Test
    public void updateShouldWork() {
        Mockito.when(birdRepository.save(bird)).thenReturn(bird);
        Bird created = service.create(bird);
        created.setName("New Name");
        created.setDescription("New Description");

        Mockito.when(birdRepository.findById(bird.getId())).thenReturn(Optional.of(bird));

        Bird updated = service.update(created);
        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getDescription()).isEqualTo("New Description");
    }
}
