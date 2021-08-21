package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Bird;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BaseService<T extends Animal> implements Service<T> {

//    private HashMap<String, T> items = new HashMap<>();

    @Override
    public Collection<T> all() {
        final ArrayList<T> ret = new ArrayList<>();
        getRepository().findAll().forEach(ret::add);
        return ret;
    }

    abstract CrudRepository<T, String> getRepository();

    @Override
    public T create(T animal) {
        String id = UUID.randomUUID().toString();
        animal.setId(id);
        return getRepository().save(animal);
        //items.put(id, animal);
    }

    @Override
    public T get(String id) {
        return getRepository().findById(id).orElse(null);
        //return items.get(id);
    }

    @Override
    public void delete(String id) {
        getRepository().findById(id).ifPresent(e -> getRepository().delete(e));
        //items.remove(id);
    }

    @Override
    public T update(T animal) {
        AtomicReference<T> ref = new AtomicReference<>();
        getRepository().findById(animal.getId()).ifPresent(e -> {
            e.setName(animal.getName());
            e.setDescription(animal.getDescription());
            T saved = getRepository().save(e);
            ref.set(saved);
        });

        return ref.get();
//        T existing = items.get(animal.getId());
//
//        if (null != existing) {
//            existing.setName(animal.getName());
//            existing.setDescription(animal.getDescription());
//        }
//
//        return existing;
    }
}
