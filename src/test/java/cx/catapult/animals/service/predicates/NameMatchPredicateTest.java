package cx.catapult.animals.service.predicates;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NameMatchPredicateTest {
    @Test
    void nameTestShouldMatchOnNullValue() {
        NameMatchPredicate nameMatchPredicate = new NameMatchPredicate(null);

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertTrue(nameMatchPredicate.test(animalEntrySet));
    }

    @Test
    void nameTestShouldMatchOnEmptyValue() {
        NameMatchPredicate nameMatchPredicate = new NameMatchPredicate("");

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertTrue(nameMatchPredicate.test(animalEntrySet));
    }

    @Test
    void nameTestShouldMatch() {
        NameMatchPredicate nameMatchPredicate = new NameMatchPredicate("Tom");

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertTrue(nameMatchPredicate.test(animalEntrySet));
    }

    @Test
    void nameTestShouldNotMatch() {
        NameMatchPredicate nameMatchPredicate = new NameMatchPredicate("Herry");

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertFalse(nameMatchPredicate.test(animalEntrySet));
    }
}