package cx.catapult.animals.domain;

import org.junit.jupiter.api.Test;

import static cx.catapult.animals.domain.Group.MAMMALS;
import static org.assertj.core.api.Assertions.assertThat;

class BaseAnimalTest {

    @Test
    void canCreateLegacyCats(){
        Cat purdey = new Cat("Purdey", "Tortie");

        assertThat(purdey.getName()).isEqualTo("Purdey");
        assertThat(purdey.getDescription()).isEqualTo("Tortie");
        assertThat(purdey.getId()).isNull();
        assertThat(purdey.getColour()).isEqualTo("n/a");
        assertThat(purdey.getType()).isEqualTo("Cat");
        assertThat(purdey.getGroup()).isEqualTo(MAMMALS);
    }

    @Test
    void canCreateAnimals() {
        BaseAnimal ellie = new BaseAnimal("Ellie", "Trunkey", MAMMALS, "Elephant", "Grey");
        assertThat(ellie.getName()).isEqualTo("Ellie");
        assertThat(ellie.getDescription()).isEqualTo("Trunkey");
        assertThat(ellie.getId()).isNull();
        assertThat(ellie.getColour()).isEqualTo("Grey");
        assertThat(ellie.getType()).isEqualTo("Elephant");
        assertThat(ellie.getGroup()).isEqualTo(MAMMALS);
    }

    @Test
    void canSetType() {
        BaseAnimal ellie = new BaseAnimal("Ellie", "Trunkey", MAMMALS, "Elephant", "Grey");
        ellie.setType("Mollusc");
        assertThat(ellie.getType()).isEqualTo("Mollusc");
    }

    @Test
    void canSetColour() {
        BaseAnimal ellie = new BaseAnimal("Ellie", "Trunkey", MAMMALS, "Elephant", "Grey");
        ellie.setColour("Brown");
        assertThat(ellie.getColour()).isEqualTo("Brown");
    }



}