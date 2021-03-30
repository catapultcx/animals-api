package cx.catapult.animals.service;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.domain.Group;
import cx.catapult.animals.repo.CrustaceanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class CrustaceanServiceTest {

    @Spy
    @InjectMocks
    private CrustaceanService crustaceanService;

    @Mock
    private CrustaceanRepository crustaceanRepository;

    @Test
    public void shouldAddCrustacean() {
        Crustacean actual = new Crustacean("Portunidae", " Portunid crabs are characterised by the " +
                "flattening of the fifth pair of legs into broad paddles, which are used for swimming");
        Crustacean expected = new Crustacean("Portunidae", " Portunid crabs are characterised by the " +
                "flattening of the fifth pair of legs into broad paddles, which are used for swimming");
        expected.setId("1");
        when(crustaceanRepository.save(any(Crustacean.class))).thenReturn(expected);
        Crustacean expectedCrustacean = crustaceanService.create(actual);
        assertNotNull(expected);
        assertEquals(expectedCrustacean.getId(), "1");
        assertEquals(expectedCrustacean.getName(), actual.getName());
        assertEquals(expectedCrustacean.getDescription(), actual.getDescription());
        assertEquals(expectedCrustacean.getGroup(), Group.INVERTEBRATE);
        verify(crustaceanRepository, times(1)).save(any(Crustacean.class));
    }

}
