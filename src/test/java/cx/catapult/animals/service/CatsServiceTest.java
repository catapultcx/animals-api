package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CatsServiceTest {

    @Autowired
    CatsService service;
    Cat cat = new Cat("Tom", "Bob cat");

    @Test
    public void createShouldWork() throws Exception {
        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        Cat actual = service.create(thisCat);
        assertThat(actual.getName()).isEqualTo(thisCat.getName());
        assertThat(actual.getDescription()).isEqualTo(thisCat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisCat.getGroup());
    }

    @Test
    void createShouldWork_whenCatIsCreatedWithId() throws Exception {
        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        thisCat.setId(UUID.randomUUID().toString());
        Cat actual = service.create(thisCat);
        assertThat(actual.getName()).isEqualTo(thisCat.getName());
        assertThat(actual.getDescription()).isEqualTo(thisCat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisCat.getGroup());
    }

    @Test
    void updateShouldWork() throws Exception {
        // given
        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        Cat myCat = service.create(thisCat);

        // and
        String updatedName = "new name";
        String updatedDescription = "new description";
        myCat.setName(updatedName);
        myCat.setDescription(updatedDescription);

        // when
        Cat updated = service.update(myCat, UUID.fromString(myCat.getId()));

        // then
        assertThat(updated.getName()).isEqualTo(updatedName);
        assertThat(updated.getDescription()).isEqualTo(updatedDescription);
    }

    @Test
    void allShouldWork() throws Exception {
        int count = service.all().size();
        service.create(cat);
        assertThat(service.all().size()).isEqualTo(count+1);
    }

    @Test
    void getShouldWork() throws Exception {
        cat = service.create(cat);
        Cat actual = service.get(cat.getId());
        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
    }

}
