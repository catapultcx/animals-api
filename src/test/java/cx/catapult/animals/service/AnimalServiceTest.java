package cx.catapult.animals.service;

import cx.catapult.animals.domain.Fish;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Colour;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalServiceTest {

    private final AnimalService<? super BaseAnimal> service = new AnimalService();

    private final Cat cat = new Cat("Tom", "Bob cat");

    @Test
    public void createShouldWork() {
        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");

        BaseAnimal actual = service.create(thisCat);

        assertThat(actual).isEqualTo(thisCat);
        assertThat(actual.getName()).isEqualTo(thisCat.getName());
        assertThat(actual.getDescription()).isEqualTo(thisCat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisCat.getGroup());
    }

    @Test
    public void createColouredAnimal() {
        Cat aBlackAndWhiteCat = new Cat("Suti", "A Black and White cat", new Colour[]{Colour.BLACK, Colour.WHITE});

        BaseAnimal catCreated = service.create(aBlackAndWhiteCat);

        assertThat(catCreated).isEqualTo(aBlackAndWhiteCat);
    }

    @Test
    @DisplayName("Must be able to store and view a list of any type of animals")
    public void mustBeAbleToViewListOfAllAnimals() {
        Cat aBlackAndWhiteCat = new Cat("Suti", "A Black and White cat", new Colour[]{Colour.BLACK, Colour.WHITE});
        Cat anotherBlackAndWhiteCat = new Cat("Suti", "A Black and White cat", new Colour[]{Colour.BLACK, Colour.WHITE});
        Fish aTransparentFish = new Fish("Happy Fish", "A Transarent fish", new Colour[]{Colour.TRANSPARENT});
        service.create(cat);
        service.create(aBlackAndWhiteCat);
        service.create(anotherBlackAndWhiteCat);
        service.create(aTransparentFish);

        Collection allAnimals = service.all();

        assertThat(allAnimals.size()).isEqualTo(4);
        assertThat(allAnimals).containsExactlyInAnyOrder(new BaseAnimal[]{cat, aBlackAndWhiteCat, anotherBlackAndWhiteCat, aTransparentFish});
    }

    @Test
    @DisplayName("Must be able to view a individual animal")
    public void mustBeAbleToViewIndividualAnimal() {
        service.create(cat);
        BaseAnimal aCatRetrivedFromDb = service.get(cat.getId());
        assertThat(aCatRetrivedFromDb).isEqualTo(cat);
    }

    @Test
    @DisplayName("Animals have same attribute but different colors are treated as different animals")
    public void animalsDifferByColourButEverythingElseIsSame() {
        Cat aBlackAndWhiteCat = new Cat("Suti", "A cat", new Colour[]{Colour.BLACK, Colour.WHITE});
        Cat aBlackCat = new Cat("Suti", "A cat", new Colour[]{Colour.BLACK});
        service.create(aBlackAndWhiteCat);
        service.create(aBlackCat);

        Collection allAnimals = service.all();

        assertThat(allAnimals.size()).isEqualTo(2);
        assertThat(allAnimals).containsExactlyInAnyOrder(new BaseAnimal[]{aBlackAndWhiteCat, aBlackCat});
        assertThat(aBlackAndWhiteCat).isNotEqualTo(aBlackCat);
    }

    // Delete
    @Test
    @DisplayName("Deletes a given animals from records")
    public void deleteGivenAnimal() {
        Cat aBlackAndWhiteCat = new Cat("Suti", "A cat", new Colour[]{Colour.BLACK, Colour.WHITE});
        Cat aBlackCat = new Cat("Suti", "A cat", new Colour[]{Colour.BLACK});
        service.create(aBlackAndWhiteCat);
        service.create(aBlackCat);
        assertThat(service.all().size()).isEqualTo(2);

        boolean deletedSuccessfully = service.delete(aBlackAndWhiteCat.getId());
        assertThat(deletedSuccessfully).isTrue();
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Returns false if given animal does not exist in records")
    public void returnsFalseOnDeleteIfGivenAnimalDoesNotExist() {
        Cat aBlackAndWhiteCat = new Cat("Suti", "A cat", new Colour[]{Colour.BLACK, Colour.WHITE});
        Cat aBlackCat = new Cat("Suti", "A cat", new Colour[]{Colour.BLACK});
        service.create(aBlackAndWhiteCat);
        service.create(aBlackCat);
        assertThat(service.all().size()).isEqualTo(2);

        boolean deletedSuccessfully = service.delete(UUID.randomUUID().toString());

        assertThat(deletedSuccessfully).isFalse();
        assertThat(service.all().size()).isEqualTo(2);
    }

    // Update
    @Test
    @DisplayName("Updates a given animal")
    public void updateGivenAnimal() {
        service.create(cat);
        assertThat(service.all().size()).isEqualTo(1);
        cat.setName("Leela");
        cat.setDescription("A cat from jungle book");

        service.update(cat);
        assertThat(cat.getName()).isEqualTo("Leela");
        assertThat(cat.getDescription()).isEqualTo("A cat from jungle book");
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Doesn't do anything if updating an non-existing animal in DB")
    public void doesNotDoAnythingIfGivenAnimalDoesNotExistInDB() {
        service.create(cat);
        String nameBeforeUpdateCall = cat.getName();
        String descBeforeUpdateCall = cat.getDescription();
        assertThat(service.all().size()).isEqualTo(1);
        cat.setId("ID does not exists");

        service.update(cat);

        assertThat(cat.getName()).isEqualTo(nameBeforeUpdateCall);
        assertThat(cat.getDescription()).isEqualTo(descBeforeUpdateCall);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void allShouldWork() {
        service.create(cat);
        Collection<Cat> allAnimals = service.all();
        assertThat(allAnimals.size()).isEqualTo(1);
        assertThat(allAnimals.iterator().next()).isEqualTo(cat);
    }

    @Test
    public void getShouldWork() {
        service.create(cat);
        BaseAnimal actual = service.get(cat.getId());
        assertThat(actual).isEqualTo(cat);
        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
    }
}
