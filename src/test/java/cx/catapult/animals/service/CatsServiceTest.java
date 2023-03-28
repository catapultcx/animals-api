package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CatsServiceTest {

  CatsService service = new CatsService();
  Cat cat = new Cat("Tom", "Bob cat");

  @Test
  public void createShouldWork() throws Exception {
    Cat thisCat = new Cat();
    thisCat.setName("Jerry");
    thisCat.setDescription("Mouse Cat");
    Cat actual = service.create(thisCat);
    assertThat(actual).isEqualTo(thisCat);
    assertThat(actual.getName()).isEqualTo(thisCat.getName());
    assertThat(actual.getDescription()).isEqualTo(thisCat.getDescription());
    assertThat(actual.getGroup()).isEqualTo(thisCat.getGroup());
  }

  @Test
  public void allShouldWork() throws Exception {
    service.create(cat);
    assertThat(service.all().size()).isEqualTo(1);
  }

  @Test
  public void getShouldWork() throws Exception {
    service.create(cat);
    Cat actual = service.get(cat.getId());
    assertThat(actual).isEqualTo(cat);
    assertThat(actual.getName()).isEqualTo(cat.getName());
    assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
    assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
  }

  @Test
  public void updateShouldWork() throws Exception {
    service.create(cat);
    String catId = cat.getId();
    Cat actual = service.get(catId);
    actual.setName("Robo");
    actual.setDescription("my new cat");
    Cat updatedActual = service.update(catId, actual);
    assertThat(updatedActual.getName()).isEqualTo("Robo");
    assertThat(updatedActual.getDescription()).isEqualTo("my new cat");
    assertThat(updatedActual.getGroup()).isEqualTo(cat.getGroup());
  }

  @Test
  public void deleteShouldWork() throws Exception {
    service.create(cat);
    String catId = cat.getId();
    service.delete(catId);
    Cat actual = service.get(catId);
    assertThat(actual).isEqualTo(null);
  }

  @Test
  public void filterShouldWork() throws Exception {
    Cat catOne = new Cat("Jerry", "my fat cat");
    Cat catTwo = new Cat("Julu", "cute little cat, like old jerry");
    service.create(catOne);
    service.create(catTwo);
    assertThat(service.filteredAll("jerry").size()).isEqualTo(2);
    assertThat(service.filteredAll("cute").size()).isEqualTo(1);
    assertThat(service.filteredAll("").size()).isEqualTo(2);
    assertThat(service.filteredAll("nice").size()).isEqualTo(0);
  }
}
