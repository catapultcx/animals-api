package cx.catapult.animals.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Animal extends BaseAnimal {

    public Animal(String name, String description, String colour, String type) {
        super(name, description, colour, type);
    }
}
