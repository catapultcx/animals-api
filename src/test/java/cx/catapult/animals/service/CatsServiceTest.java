package cx.catapult.animals.service;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CatsServiceTest {

    @Mock
    CatRepository catRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks

    CatsService service;
    Cat cat = new Cat("Tom", "Bob cat");

    @Test
    public void createShouldWork() throws Exception {
        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");

        Mockito.when(catRepository.save(thisCat)).thenReturn(thisCat);

        Cat actual = service.create(thisCat);
        assertThat(actual).isEqualTo(thisCat);
        assertThat(actual.getName()).isEqualTo(thisCat.getName());
        assertThat(actual.getDescription()).isEqualTo(thisCat.getDescription());
        assertThat(actual.getAnimalGroup()).isEqualTo(thisCat.getAnimalGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(cat);

        ArrayList<Cat> allCats = new ArrayList<>();
        allCats.add(cat);
        Mockito.when(catRepository.findAll()).thenReturn(allCats);

        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        Mockito.when(catRepository.save(cat)).thenReturn(cat);

        service.create(cat);

        Mockito.when(catRepository.findById(cat.getId())).thenReturn(Optional.of(cat));

        Cat actual = service.get(cat.getId());
        assertThat(actual).isEqualTo(cat);
        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getAnimalGroup()).isEqualTo(cat.getAnimalGroup());
    }

    @Test
    public void deleteShouldWork() {
        Mockito.when(catRepository.save(cat)).thenReturn(cat);

        service.create(cat);

        ArrayList<Cat> allCats = new ArrayList<>();
        allCats.add(cat);
        Mockito.when(catRepository.findAll()).thenReturn(allCats);

        int size  = service.all().size();
        assertThat(size).isEqualTo(1);

        allCats.clear();

        service.delete(cat.getId());
        int size2  = service.all().size();
        assertThat(size2).isEqualTo(0);
    }
    @Test
    public void updateShouldWork() {
        Mockito.when(catRepository.save(cat)).thenReturn(cat);

        Cat created = service.create(cat);
        created.setName("New Name");
        created.setDescription("New Description");

        Mockito.when(catRepository.findById(cat.getId())).thenReturn(Optional.of(cat));

        Cat updated = service.update(created);
        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getDescription()).isEqualTo("New Description");
    }

}
