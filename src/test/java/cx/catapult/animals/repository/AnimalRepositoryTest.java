package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Group;
import cx.catapult.animals.entity.AnimalEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AnimalRepositoryTest {

    @Autowired
    AnimalRepository animalRepository;

    @BeforeEach
    public void setUp() {

    }
    @Test
    void animalRepository_whenInsertCalled_shouldAddAnimal() {
        AnimalEntity entity = generateEntity("Tom","cat",Group.MAMMALS,"blue");
        AnimalEntity value = animalRepository.save(entity);
        assertThat(value).isNotNull();
        assertThat(value.getId()).isNotNull();

    }

    private static AnimalEntity generateEntity(String name, String type, Group group, String colorString) {
        AnimalEntity entity = new AnimalEntity();
        entity.setType(type);
        entity.setName(name);
        entity.setColor(colorString);
        entity.setGroup(group);
        entity.setDescription("%s description".formatted(name));
        return entity;
    }

}
