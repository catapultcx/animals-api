package cx.catapult.animals.service.predicates;

import cx.catapult.animals.domain.BaseAnimal;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.Predicate;

public class DescriptionMatchPredicate implements Predicate<Map.Entry<String, BaseAnimal>> {
    private String description;

    public DescriptionMatchPredicate(String description) {
        this.description = description;
    }
    @Override
    public boolean test(Map.Entry<String, BaseAnimal> record) {
        if (StringUtils.isNotEmpty(description)) {
            return record.getValue().getDescription().equals(description);
        } else {
            return true;
        }
    }
}
