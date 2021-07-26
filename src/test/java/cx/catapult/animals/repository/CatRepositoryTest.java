package cx.catapult.animals.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cx.catapult.animals.domain.Group;
import cx.catapult.animals.entity.CatEntity;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CatRepositoryTest {

    @Autowired
    private CatRepository catRepository;

    @Test
    void testFindById() {
        // given
        UUID id = UUID.fromString("4f2e85b9-57bd-4668-9631-4fc2041f9f1f");

        // and
        String expectedName = "Charlie";
        String expectedDescription = "my wonderful cat!";
        Group expectedGenus = Group.MAMMALS;

        // and
        CatEntity expected = new CatEntity();
        expected.setName(expectedName);
        expected.setId(id);
        expected.setDescription(expectedDescription);
        expected.setGenus(expectedGenus);

        // and
        catRepository.save(expected);

        // when
        CatEntity actual = catRepository.findById(id).orElse(new CatEntity());

        // then
        assertThat(actual.getName()).isEqualTo(expectedName);
        assertThat(actual.getGenus()).isEqualTo(expectedGenus);
        assertThat(actual.getDescription()).isEqualTo(expectedDescription);
    }

}
