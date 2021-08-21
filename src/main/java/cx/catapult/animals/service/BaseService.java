package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BaseService<T extends Animal> implements Service<T> {

    @Override
    public Collection<T> all() {
        final ArrayList<T> ret = new ArrayList<>();
        getRepository().findAll().forEach(ret::add);
        return ret;
    }

    abstract CrudRepository<T, String> getRepository();

    @Override
    @Transactional
    public T create(T animal) {
        final String id = UUID.randomUUID().toString();
        animal.setId(id);
        // System.out.println("Saving " + animal.getId() + ":" + animal.getName());
        return getRepository().save(animal);
    }

    @Override
    public T get(String id) {
        return getRepository().findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(String id) {
        getRepository().findById(id).ifPresent(e -> getRepository().delete(e));
    }

    @Override
    @Transactional
    public T update(T animal) {
        AtomicReference<T> ref = new AtomicReference<>();
        getRepository().findById(animal.getId()).ifPresent(e -> {
            e.setName(animal.getName());
            e.setDescription(animal.getDescription());
            T saved = getRepository().save(e);
            ref.set(saved);
        });

        return ref.get();
    }
}
