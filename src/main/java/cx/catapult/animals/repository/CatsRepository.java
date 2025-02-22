package cx.catapult.animals.repository;

import cx.catapult.animals.domain.Cat;
import java.util.List;

public interface CatsRepository {
    Cat create(Cat entity);
    Cat get(String id);
    List<Cat> all();
} 