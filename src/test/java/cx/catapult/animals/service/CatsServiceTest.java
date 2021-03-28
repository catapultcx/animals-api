package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CatsServiceTest {

    @Mock
    private StorageService<Cat> storageService;

    @InjectMocks
    private CatsService service;

    Cat cat = new Cat("Tom", "Bob cat");

    @Test
    public void createShouldWork() throws Exception {
        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        when(service.create(any())).thenReturn(thisCat);
        Cat actual = service.create(thisCat);
        assertThat(actual).isEqualTo(thisCat);
        assertThat(actual.getName()).isEqualTo(thisCat.getName());
        assertThat(actual.getDescription()).isEqualTo(thisCat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisCat.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        when(service.create(any())).thenReturn(cat);
        when(service.all()).thenReturn(Arrays.asList(cat));
        service.create(cat);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        when(service.get(any())).thenReturn(cat);
        Cat actual = service.get(cat.getId());
        assertThat(actual).isEqualTo(cat);
        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
    }

    @Test
    public void updateShouldWork() {
        final Cat updateCat = new Cat("Jerry", "Sneaky Lizard");
        when(service.update(any(), any())).thenReturn(updateCat);
        when(service.all()).thenReturn(Arrays.asList(updateCat));
        service.update(cat.getId(), updateCat);
        final Collection<Cat> all = service.all();
        assertThat(all).contains(updateCat);
        assertThat(all).doesNotContain(cat);
    }
}
