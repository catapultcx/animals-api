package cx.catapult.animals.service.predicates;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TypeMatchPredicateTest {
    @Test
    void typeTestShouldMatchOnNullValue() {
        TypeMatchPredicate typeMatchPredicate = new TypeMatchPredicate(null);

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertTrue(typeMatchPredicate.test(animalEntrySet));
    }

    @Test
    void typeTestShouldMatchOnEmptyValue() {
        TypeMatchPredicate typeMatchPredicate = new TypeMatchPredicate("");

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertTrue(typeMatchPredicate.test(animalEntrySet));
    }

    @Test
    void typeTestShouldMatch() {
        TypeMatchPredicate typeMatchPredicate = new TypeMatchPredicate("Cat");

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertTrue(typeMatchPredicate.test(animalEntrySet));
    }

    @Test
    void typeTestShouldNotMatch() {
        TypeMatchPredicate typeMatchPredicate = new TypeMatchPredicate("Dog");

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertFalse(typeMatchPredicate.test(animalEntrySet));
    }
}