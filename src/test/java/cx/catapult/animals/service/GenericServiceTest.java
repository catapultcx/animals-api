package cx.catapult.animals.service;

import cx.catapult.animals.domain.Generic;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GenericServiceTest {

    GenericService service = new GenericService();
    Generic generic = new Generic("Lara", "Little lizard", "Sand lizard", Group.REPTILES);

    @Test
    public void createShouldWork() throws Exception {
        Generic thisGeneric = new Generic();
        thisGeneric.setName("Jerry");
        thisGeneric.setDescription("Mouse Cat");
        Generic actual = service.create(thisGeneric);
        assertThat(actual).isEqualTo(thisGeneric);
        assertThat(actual.getName()).isEqualTo(thisGeneric.getName());
        assertThat(actual.getDescription()).isEqualTo(thisGeneric.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisGeneric.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(generic);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(generic);
        Generic actual = service.get(generic.getId());
        assertThat(actual).isEqualTo(generic);
        assertThat(actual.getName()).isEqualTo(generic.getName());
        assertThat(actual.getDescription()).isEqualTo(generic.getDescription());
        assertThat(actual.getGroup()).isEqualTo(generic.getGroup());
    }

    @Test
    public void deleteShouldWork() throws Exception {
        service.create(generic);
        Generic actual = service.get(generic.getId());
        assertThat(actual).isEqualTo(generic);
        boolean deleted = service.delete(generic.getId());
        assertThat(deleted).isTrue();
        Generic actual2 = service.get(generic.getId());
        assertThat(actual2).isNull();
    }

    @Test
    public void updateShouldWork() throws Exception {
        service.create(generic);
        Generic actual = service.get(generic.getId());
        assertThat(actual).isEqualTo(generic);
        String newSpecies = "Puffer fish";
        generic.setSpecies(newSpecies);
        generic.setGroup(Group.FISH);
        Generic updated = service.update(actual);
        assertThat(updated.getSpecies()).isEqualTo(newSpecies);
        assertThat(updated.getGroup()).isEqualTo(Group.FISH);
    }

}
