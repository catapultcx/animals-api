package cx.catapult.animals.service.predicates;

import cx.catapult.animals.domain.BaseAnimal;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.Predicate;

public class NameMatchPredicate implements Predicate<Map.Entry<String, BaseAnimal>> {
    private String name;

    public NameMatchPredicate(String name) {
        this.name = name;
    }
    @Override
    public boolean test(Map.Entry<String, BaseAnimal> record) {
        if (StringUtils.isNotEmpty(name)) {
            return record.getValue().getName().equals(name);
        } else {
            return true;
        }
    }
}
