package cx.catapult.animals.service;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.domain.Group;
import cx.catapult.animals.repo.CrustaceanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class CrustaceanServiceTest {

    @InjectMocks
    private CrustaceanService crustaceanService;

    @Mock
    private CrustaceanRepository crustaceanRepository;

    @Test
    public void shouldAddCrustacean() {
        Crustacean inputCrustacean = new Crustacean("Portunidae", " Portunid crabs are characterised by the " +
                "flattening of the fifth pair of legs into broad paddles, which are used for swimming");
        Crustacean expectedCrustacean = new Crustacean("Portunidae", " Portunid crabs are characterised by the " +
                "flattening of the fifth pair of legs into broad paddles, which are used for swimming");
        expectedCrustacean.setId("1");
        when(crustaceanRepository.save(any(Crustacean.class))).thenReturn(expectedCrustacean);
        Crustacean  actualCrustacean = crustaceanService.create(inputCrustacean);
        assertNotNull(actualCrustacean);
        assertThat(actualCrustacean.getId(), is(equalTo("1")));
        assertThat(actualCrustacean.getName(), is(equalTo(inputCrustacean.getName())));
        assertThat(actualCrustacean.getDescription(), is(equalTo(inputCrustacean.getDescription())));
        assertThat(actualCrustacean.getGroup(), is(equalTo(Group.INVERTEBRATE)));
        verify(crustaceanRepository, times(1)).save(any(Crustacean.class));
    }

    @Test
    public void shouldListAllCrustaceans(){
        Crustacean expected1 = new Crustacean("Portunidae", " Portunid crabs are characterised by the " +
                "flattening of the fifth pair of legs into broad paddles, which are used for swimming");
        expected1.setId("1");
        Crustacean expected2 = new Crustacean("Portunidae", " Portunid crabs are characterised by the " +
                "flattening of the fifth pair of legs into broad paddles, which are used for swimming");
        expected2.setId("2");
        List<Crustacean> expectedCrustaceans = Arrays.asList(new Crustacean[]{expected1, expected2});

        when(crustaceanRepository.findAll()).thenReturn(expectedCrustaceans);
        List<Crustacean> actualCrustaceans = crustaceanService.all();
        assertNotNull(actualCrustaceans);
        assertTrue(!actualCrustaceans.isEmpty());
        assertThat(expectedCrustaceans.size(), is(equalTo(2)));

        assertThat(expectedCrustaceans.get(0).getId(), is(equalTo(expected1.getId())));
        assertThat(expectedCrustaceans.get(0).getName(), is(equalTo(expected1.getName())));
        assertThat(expectedCrustaceans.get(0).getDescription(), is(equalTo(expected1.getDescription())));
        assertThat(expectedCrustaceans.get(0).getGroup(), is(equalTo(expected1.getGroup())));

        assertThat(expectedCrustaceans.get(1).getId(), is(equalTo(expected2.getId())));
        assertThat(expectedCrustaceans.get(1).getName(), is(equalTo(expected2.getName())));
        assertThat(expectedCrustaceans.get(1).getDescription(), is(equalTo(expected2.getDescription())));
        assertThat(expectedCrustaceans.get(1).getGroup(), is(equalTo(expected2.getGroup())));

        verify(crustaceanRepository, times(1)).findAll();
    }

}
