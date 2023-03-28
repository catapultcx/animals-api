package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseService<T extends Animal> implements Service<T> {

  private HashMap<String, T> items = new HashMap<>();

  @Override
  public Collection<T> all() {
    return items.values();
  }

  @Override
  public T create(T animal) {
    String id = UUID.randomUUID().toString();
    animal.setId(id);
    items.put(id, animal);
    return animal;
  }

  @Override
  public T get(String id) {
    return items.get(id);
  }

  @Override
  public Collection<T> filteredAll(String searchString) {
    return items.entrySet().stream()
      .filter(entry -> {
        T rowData = entry.getValue();
        return rowData.getName().toLowerCase().contains(searchString.toLowerCase()) || rowData.getDescription().toLowerCase().contains(searchString.toLowerCase());
      }).map(Map.Entry::getValue)
      .collect(Collectors.toList());
  }

  @Override
  public T update(String id, T animal) {
    if (items.containsKey(id)) {
      items.put(id, animal);
    }
    return animal;
  }

  @Override
  public T delete(String id) {
    T currentData = items.get(id);
    if (items.containsKey(id)) {
      items.remove(id);
    }
    return currentData;
  }
}
