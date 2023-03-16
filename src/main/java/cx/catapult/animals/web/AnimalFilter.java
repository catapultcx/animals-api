package cx.catapult.animals.web;

import org.apache.commons.lang3.StringUtils;

public class AnimalFilter {
    private String name;
    private String type;
    private String color;
    private String description;

    public AnimalFilter(String name, String type, String color, String description) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasParameters() {
        return StringUtils.isNotBlank(name) || StringUtils.isNotBlank(type) ||
        StringUtils.isNotBlank(color) || StringUtils.isNotBlank(description);
    }
}
