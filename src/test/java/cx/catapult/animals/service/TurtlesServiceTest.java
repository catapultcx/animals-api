package cx.catapult.animals.service;

import cx.catapult.animals.domain.Turtle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TurtlesServiceTest {

    TurtlesService service = new TurtlesService();
    Turtle turtle = new Turtle("piggy", "Pig nosed");

    @Test
    public void createShouldWork() throws Exception {
        Turtle thisTurtle = new Turtle();
        thisTurtle.setName("african");
        thisTurtle.setDescription("African Helmeted");
        Turtle actual = service.create(thisTurtle);
        assertThat(actual).isEqualTo(thisTurtle);
        assertThat(actual.getName()).isEqualTo(thisTurtle.getName());
        assertThat(actual.getDescription()).isEqualTo(thisTurtle.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisTurtle.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(turtle);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(turtle);
        Turtle actual = service.get(turtle.getId());
        assertThat(actual).isEqualTo(turtle);
        assertThat(actual.getName()).isEqualTo(turtle.getName());
        assertThat(actual.getDescription()).isEqualTo(turtle.getDescription());
        assertThat(actual.getGroup()).isEqualTo(turtle.getGroup());
    }
}
