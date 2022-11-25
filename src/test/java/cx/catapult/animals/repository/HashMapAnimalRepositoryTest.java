package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashMapAnimalRepositoryTest {

    private AnimalRepository animalRepository;

    private static final String OWNER_ID = "1234";

    @BeforeEach
    void setUp() {
        animalRepository = new HashMapAnimalRepository();
    }

    @Test
    void testCanAddARecord() {
        var recordToAdd = new Animal(null, "type", "name", "description", "description");

        var animal = animalRepository.createAnimalForOwner(OWNER_ID, recordToAdd);
        Collection<Animal> allAvailableEvents = animalRepository.getAllAnimalsForOwner(OWNER_ID);

        var expectedRecord = new Animal(animal.id(), "type", "name", "description", "description");
        assertEquals(1, allAvailableEvents.size());
        assertTrue(allAvailableEvents.contains(expectedRecord));
    }

    @Test
    void testThatWeCanAddMultipleAnimalsForSameOwnerId() {
        var recordToAdd = new Animal(null, "type", "name", "description", "description");

        var animal = animalRepository.createAnimalForOwner(OWNER_ID, recordToAdd);

        var expectedRecord = new Animal(animal.id(), "type", "name", "description", "description");

        Collection<Animal> allAvailableEvents = animalRepository.getAllAnimalsForOwner(OWNER_ID);
        assertEquals(1, allAvailableEvents.size());
        assertTrue(allAvailableEvents.contains(expectedRecord));


        var nextRecordToAdd = new Animal(null, "type", "name2", "colour", "description");
        var animal1 = animalRepository.createAnimalForOwner(OWNER_ID, nextRecordToAdd);
        var expected2 = new Animal(animal1.id(), "type", "name2", "colour", "description");
        Collection<Animal> expectedEvents = new ArrayList<>();
        expectedEvents.add(expectedRecord);
        expectedEvents.add(expected2);

        Collection<Animal> allAvailableEventsAfterUpdate = animalRepository.getAllAnimalsForOwner(OWNER_ID);
        assertEquals(2, allAvailableEventsAfterUpdate.size());
        assertTrue(allAvailableEventsAfterUpdate.containsAll(expectedEvents));
    }

    @Test
    void testWhenAddingAMultipleRecordItSucceeds() {
        var recordToAdd = new Animal(null, "type", "name", "description", "description");
        var anotherRecordToAdd = new Animal(null, "type", "name2", "colour", "description");
        final String ownderId2 = "1234";

        var animal = animalRepository.createAnimalForOwner(OWNER_ID, recordToAdd);
        var animal1 = animalRepository.createAnimalForOwner(ownderId2, anotherRecordToAdd);

        var expectedRecord1 = new Animal(animal.id(), "type", "name", "description", "description");
        var expectedRecord2 = new Animal(animal1.id(), "type", "name2", "colour", "description");

        Collection<Animal> expectedList = new ArrayList<>();
        expectedList.add(expectedRecord1);
        expectedList.add(expectedRecord2);

        Collection<Animal> allAvailableEvents = animalRepository.getAllAnimalsForOwner(OWNER_ID);

        assertEquals(2, allAvailableEvents.size());
        assertTrue(allAvailableEvents.containsAll(expectedList));
    }

    @Test
    void testGettingAnForOwnerItSucceeds() {
        var recordToAdd = new Animal(null, "type", "name", "description", "description");
        var anotherRecordToAdd = new Animal(null, "type", "name2", "colour", "description");
        final String ownderId2 = "1234";

        var animal = animalRepository.createAnimalForOwner(OWNER_ID, recordToAdd);
        var animal1 = animalRepository.createAnimalForOwner(ownderId2, anotherRecordToAdd);


        var animalForOwner = animalRepository.getAnimalForOwner(OWNER_ID, animal.id());

        assertEquals("name", animalForOwner.name());
        assertEquals(animal.id(), animalForOwner.id());
        assertNotEquals(animal1.id(), animalForOwner.id());
    }

}