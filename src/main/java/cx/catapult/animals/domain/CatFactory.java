package cx.catapult.animals.domain;

import cx.catapult.animals.entity.CatEntity;
import java.util.UUID;

public class CatFactory {

    public static Cat fromCatEntity(CatEntity catEntity) {
        Cat cat = new Cat("", "");
        cat.setDescription(catEntity.getDescription());
        cat.setName(catEntity.getName());
        cat.setId(catEntity.getId().toString());
        return cat;
    }

    public static CatEntity fromCat(Cat cat) {
        CatEntity catEntity = new CatEntity();
        catEntity.setGenus(Group.MAMMALS);
        catEntity.setDescription(cat.getDescription());
        catEntity.setName(cat.getName());
        if (cat.getId() != null) {
            catEntity.setId(UUID.fromString(cat.getId()));
        }
        return catEntity;
    }
}
