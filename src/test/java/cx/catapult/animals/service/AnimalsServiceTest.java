package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnimalsServiceTest {

    @Test
    void canImportLegacyCats() {
        CatsService catsService = mock(CatsService.class);

        Cat minnie = new Cat("Minnie", "Fluffy");
        Cat purdey = new Cat("Purdey", "Curious");
        when(catsService.all()).thenReturn(Set.of(minnie, purdey));

        AnimalsService animalsService = new AnimalsService(catsService);
        animalsService.initialize();

        assertThat(animalsService.all()).extracting("name").containsExactlyInAnyOrder(minnie.getName(), purdey.getName());
        assertThat(animalsService.all()).extracting("description").containsExactlyInAnyOrder(minnie.getDescription(), purdey.getDescription());
    }
}