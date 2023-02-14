package cx.catapult.animals.domain;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated()
public class Cat {

    private Animal animal;

    public Cat() {
        this.animal = new Animal("cat", "", "");
    }

    public Cat(String name, String description) {
        this.animal = new Animal("cat", name, description);
    }

    public Cat(Animal animal) {
        this.animal = animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getId() {
        return this.animal.getId();
    }

    public void setId(String id) {
        this.animal.setId(id);
    }

    public String getName() {
        return animal.getName();
    }

    public void setName(String name) {
        this.animal.setName(name);
    }

    public String getDescription() {
        return this.animal.getDescription();
    }

    public void setDescription(String description) {
        this.animal.setDescription(description);
    }

    public Group getGroup() {
        return this.animal.getGroup();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cat)) return false;
        Cat cat = (Cat) o;
        return animal.equals(cat.animal);
    }

    public static Cat fromAnimal(Animal animal) {
        return new Cat(animal);
    }

    public static List<Cat> fromAnimals(Collection<Animal> animals) {
        return animals.stream().map(Cat::new).collect(Collectors.toList());
    }
}
