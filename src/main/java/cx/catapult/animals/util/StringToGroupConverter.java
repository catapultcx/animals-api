package cx.catapult.animals.util;

import cx.catapult.animals.domain.Group;
import org.springframework.core.convert.converter.Converter;

public class StringToGroupConverter implements Converter<String, Group> {
    @Override
    public Group convert(String source) {
        return Group.valueOf(source.toUpperCase());
    }
}
