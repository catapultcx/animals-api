package cx.catapult.animals.service.predicates;

import cx.catapult.animals.domain.BaseAnimal;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.Predicate;

public class ColorMatchPredicate implements Predicate<Map.Entry<String, BaseAnimal>> {
    private String color;

    public ColorMatchPredicate(String name) {
        this.color = name;
    }
    @Override
    public boolean test(Map.Entry<String, BaseAnimal> record) {
        if (StringUtils.isNotEmpty(color)) {
            return record.getValue().getColor().equals(color);
        } else {
            return true;
        }
    }
}
