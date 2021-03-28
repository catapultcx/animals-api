package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Group;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name="storage.persistant", havingValue = "false")
@Component
@Scope("prototype")
public class InMemoryStorageService<T extends Animal> implements StorageService<T> {
    private HashMap<String, T> items = new HashMap<>();
    private Group group;

    @Override
    public Collection<T> all() {
        return items.values();
    }

    @Override
    public T create(T animal) {
        String id = UUID.randomUUID()
                        .toString();
        animal.setId(id);
        items.put(id, animal);
        return animal;
    }

    @Override
    public T get(String id) {
        return items.get(id);
    }

    @Override
    public void delete(final String id) {
        items.remove(id);
    }

    @Override
    public T update(final String id,
                    final T newAnimal) {
        newAnimal.setId(id);
        items.replace(id, newAnimal);
        return items.get(id);
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(final Group group) {

    }
}
