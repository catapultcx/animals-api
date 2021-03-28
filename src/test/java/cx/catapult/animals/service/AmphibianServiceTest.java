package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cx.catapult.animals.domain.BaseAmphibian;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.security.RunAs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AmphibianServiceTest {
    @Mock
    private StorageService<BaseAmphibian> service;

    @InjectMocks
    private AmphibianService underTest;

    @Test
    public void shouldStoreAmphibianInformation() {
        final BaseAmphibian testAmphibian = new BaseAmphibian("testAmphib", "This amphibian is a test");
        when(service.create(any())).thenReturn(testAmphibian);
        final BaseAmphibian actual = underTest.create(testAmphibian);
        assertThat(actual).isEqualTo(testAmphibian);
        assertThat(actual.getName()).isEqualTo(testAmphibian.getName());
        assertThat(actual.getDescription()).isEqualTo(testAmphibian.getDescription());
        assertThat(actual.getGroup()).isEqualTo(testAmphibian.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        final BaseAmphibian testAmphibian = new BaseAmphibian("testAmphib", "This amphibian is a test");
        when(service.create(any())).thenReturn(testAmphibian);
        when(service.all()).thenReturn(Arrays.asList(testAmphibian));
        underTest.create(testAmphibian);
        assertThat(underTest.all()
                            .size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        final BaseAmphibian testAmphibian = new BaseAmphibian("testAmphib", "This amphibian is a test");
        when(service.get(any())).thenReturn(testAmphibian);
        final BaseAmphibian actual = underTest.get(testAmphibian.getId());
        verify(service).get(testAmphibian.getId());
        assertThat(actual).isEqualTo(testAmphibian);
        assertThat(actual.getName()).isEqualTo(testAmphibian.getName());
        assertThat(actual.getDescription()).isEqualTo(testAmphibian.getDescription());
        assertThat(actual.getGroup()).isEqualTo(testAmphibian.getGroup());
    }

    @Test
    public void deleteShouldWork() {
        final BaseAmphibian testAmphibian = new BaseAmphibian("testAmphib", "This amphibian is a test");
        when(service.create(any())).thenReturn(testAmphibian);
        doNothing().when(service).delete(any());
        underTest.create(testAmphibian);
        underTest.delete(testAmphibian.getId());
        verify(service).delete(testAmphibian.getId());
        final Collection<BaseAmphibian> all = underTest.all();
        assertThat(all).doesNotContain(testAmphibian);
    }

    @Test
    public void updateShouldWork() {
        final BaseAmphibian testAmphibian = new BaseAmphibian("testAmphib", "This amphibian is a test");
        final BaseAmphibian updateAmphibian = new BaseAmphibian("Jerry", "Sneaky Lizard");
        when(service.update(any(), any())).thenReturn(updateAmphibian);
        when(service.all()).thenReturn(Arrays.asList(updateAmphibian));
        underTest.update(testAmphibian.getId(), updateAmphibian);
        verify(service).update(testAmphibian.getId(), updateAmphibian);
        final Collection<BaseAmphibian> all = underTest.all();
        assertThat(all).contains(updateAmphibian);
    }
}
