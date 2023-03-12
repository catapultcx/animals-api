package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;

import java.util.Collection;

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
    void filterShouldWork_when_passNoParameter() throws Exception {
        //given
        createCats();

        //when
        Collection<Cat> allCats = service.getByFilter("","Jerry");

        //then
        assertThat(allCats).hasSize(1);
    }

    @Test
    void givenCat_whenFilterShouldWork_when_passOnlyName() throws Exception {
        //given
        createCats();

        //when
        Collection<Cat> cats = service.getByFilter("T","");

        //then
        assertThat(cats).hasSize(3);
    }

    @Test
    void givenCats_whenFilteredCatsByDescription_thenReturnsTwoCats() throws Exception {
        //given
        createCats();

        //when
        Collection<Cat> cats = service.getByFilter("","sCarY");

        //then
        assertThat(cats).hasSize(2);
    }

    @Test
    void givenCats_whenFilteredCatsByAllParameters_thenReturnsTwoCats() throws Exception {
        //given
        createCats();

        //when
        Collection<Cat> cats = service.getByFilter("Ali","sCarY");

        //then
        assertThat(cats).isEmpty();
    }

    @Test
    void givenCat_whenDeleteCatById_thenDeleteCat() throws Exception {
        //given
        service.create(cat);
        Collection<Cat> cats = service.all();

        //when
        service.delete(cat.getId());

        //then
        assertThat(cats).isEmpty();
    }

    @Test
    void givenCat_whenUpdateCat_thenCatUpdated() throws Exception {
        //given
        String newName = "Alice";
        String newDescription = "wild Child";

        service.create(cat);

        cat.setName(newName);
        cat.setDescription(newDescription);

        //when
        service.update(cat);

        //then
        assertThat(newName).isEqualTo(cat.getName());
        assertThat(newDescription).isEqualTo(cat.getDescription());
    }

    void createCats() {
        service.create(new Cat("Tom", "Friend of Jerry"));
        service.create(new Cat("Jerry", "Not really a cat"));
        service.create(new Cat("Bili", "Furry cat"));
        service.create(new Cat("Smelly", "Cat with friends"));
        service.create(new Cat("Tiger", "Large cat"));
        service.create(new Cat("Tigger", "Not a scary cat"));
        service.create(new Cat("Garfield", "Lazy cat"));
        service.create(new Cat("Alice", "My sister's cAt"));
        service.create(new Cat("Jimmy", "Wild and scarY child"));
        service.create(new Cat("Jojo", "Adventurist cat"));
        service.create(new Cat("Poncik", "My cat"));
    }
}
