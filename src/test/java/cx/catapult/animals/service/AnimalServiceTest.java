package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

public class AnimalServiceTest {

    CatsService catsService = new CatsService();
    InsectsService insectsService = new InsectsService();
    AnimalService service = new AnimalService(catsService, insectsService);
    BaseAnimal animal = new BaseAnimal("Tom", "Bob cat", Group.MAMMALS);

    @Test
    public void resultsShouldWork() throws Exception {
        service.create(animal);
        service.results("Tom");
        assertThat(service.all().size()).isEqualTo(1);
    }

}
