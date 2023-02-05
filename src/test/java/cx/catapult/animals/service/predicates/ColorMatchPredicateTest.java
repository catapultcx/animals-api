package cx.catapult.animals.service.predicates;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ColorMatchPredicateTest {
    @Test
    void colorTestShouldMatchOnNullValue() {
        ColorMatchPredicate colorMatchPredicate = new ColorMatchPredicate(null);

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertTrue(colorMatchPredicate.test(animalEntrySet));
    }

    @Test
    void colorTestShouldMatchOnEmptyValue() {
        ColorMatchPredicate colorMatchPredicate = new ColorMatchPredicate("");

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertTrue(colorMatchPredicate.test(animalEntrySet));
    }

    @Test
    void colorTestShouldMatch() {
        ColorMatchPredicate colorMatchPredicate = new ColorMatchPredicate("Grey");

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertTrue(colorMatchPredicate.test(animalEntrySet));
    }

    @Test
    void colorTestShouldNotMatch() {
        ColorMatchPredicate colorMatchPredicate = new ColorMatchPredicate("Red");

        BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey", "Grey Big", Group.MAMMALS);
        Map.Entry<String, BaseAnimal> animalEntrySet = new AbstractMap.SimpleEntry<>("1", cat);
        assertFalse(colorMatchPredicate.test(animalEntrySet));
    }
}