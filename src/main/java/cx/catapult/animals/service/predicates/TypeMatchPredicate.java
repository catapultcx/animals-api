package cx.catapult.animals.service.predicates;

import cx.catapult.animals.domain.BaseAnimal;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.Predicate;

public class TypeMatchPredicate implements Predicate<Map.Entry<String, BaseAnimal>> {
    private String type;

    public TypeMatchPredicate(String type) {
        this.type = type;
    }
    @Override
    public boolean test(Map.Entry<String, BaseAnimal> record) {
        if (StringUtils.isNotEmpty(type)) {
            return record.getValue().getType().equals(type);
        } else {
            return true;
        }
    }
}
