package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;

public abstract class BaseService<T extends Animal> implements Service<T> {

    private HashMap<String, T> items = new HashMap<>();
}
