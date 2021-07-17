package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Arachnid;
import cx.catapult.animals.domain.Cat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DefaultSearchServiceTest {
    @Mock
    private Service<Cat> catService;

    @Mock
    private Service<Arachnid> arachnidService;

    private DefaultSearchService defaultSearchService;

    private static final String CAT_ID = UUID.randomUUID().toString();
    private static final String ARACHNID_ID = UUID.randomUUID().toString();

    private Cat cat = new Cat("Cat name", "Cat description");
    private Arachnid arachnid = new Arachnid("Arachnid name", "Arachnid description");

    @BeforeEach
    public void setUp(){
        cat.setId(CAT_ID);
        arachnid.setId(ARACHNID_ID);

        when(catService.all()).thenReturn(Arrays.asList(cat));
        when(arachnidService.all()).thenReturn(Arrays.asList(arachnid));

        defaultSearchService = new DefaultSearchService(catService, arachnidService);
    }

    @Test
    public void shouldReturnACatById(){
        Animal animal = defaultSearchService.searchById(CAT_ID).orElseThrow(() -> new RuntimeException("Failed"));

        assertThat(animal instanceof Cat).isTrue();
    }

    @Test
    public void shouldReturnACatByName(){
        Animal animal = defaultSearchService.searchByName("Cat name").orElseThrow(() -> new RuntimeException("Failed"));

        assertThat(animal instanceof Cat).isTrue();
    }

    @Test
    public void shouldReturnASpiderById(){
        Animal animal = defaultSearchService.searchById(ARACHNID_ID).orElseThrow(() -> new RuntimeException("Failed"));

        assertThat(animal instanceof Arachnid).isTrue();
    }

    @Test
    public void shouldReturnASpiderByName(){
        Animal animal = defaultSearchService.searchByName("Arachnid name").orElseThrow(() -> new RuntimeException("Failed"));

        assertThat(animal instanceof Arachnid).isTrue();
    }


}
