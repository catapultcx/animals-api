package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class CatsServiceTest {

    private CatsService service;
    private final Cat TOM_CAT = new Cat("Tom", "Bob cat");
    private final Cat GARFIELD_CAT = new Cat("Garfield", "Cool cat");

    @BeforeEach
    public void setUp() {
        service = new CatsService();
    }

    @Test
    public void shouldLoadAll() {
        service.create(TOM_CAT);
        service.create(GARFIELD_CAT);

        Collection<Cat> cats = service.all();

        assertThat(cats.size()).isEqualTo(2);
        assertThat(cats.contains(TOM_CAT)).isTrue();
        assertThat(cats.contains(GARFIELD_CAT)).isTrue();
    }

    @Test
    public void shouldLoadById() {
        service.create(TOM_CAT);

        final Cat actual = service.get(TOM_CAT.getId());

        assertThat(actual).isEqualTo(TOM_CAT);
        assertThat(actual.getName()).isEqualTo(TOM_CAT.getName());
        assertThat(actual.getDescription()).isEqualTo(TOM_CAT.getDescription());
        assertThat(actual.getGroup()).isEqualTo(TOM_CAT.getGroup());
    }

    @Test
    public void shouldCreate() {
        final Cat actual = service.create(TOM_CAT);

        assertThat(actual).isEqualTo(TOM_CAT);
        assertThat(actual.getName()).isEqualTo(TOM_CAT.getName());
        assertThat(actual.getDescription()).isEqualTo(TOM_CAT.getDescription());
        assertThat(actual.getGroup()).isEqualTo(TOM_CAT.getGroup());
    }

    @Test
    public void shouldUpdate() {
        final Cat created = service.create(TOM_CAT);
        assertThat(created).isEqualTo(TOM_CAT);

        final Cat TOM_CAT_UPDATE = new Cat("Tom Sawyer", "The bobcat");

        final Cat updated = service.update(TOM_CAT.getId(), TOM_CAT_UPDATE);

        assertThat(updated).isNotEqualTo(TOM_CAT);
        assertThat(updated).isEqualTo(TOM_CAT_UPDATE);
        assertThat(updated.getName()).isEqualTo(TOM_CAT_UPDATE.getName());
        assertThat(updated.getDescription()).isEqualTo(TOM_CAT_UPDATE.getDescription());
        assertThat(updated.getGroup()).isEqualTo(TOM_CAT_UPDATE.getGroup());
    }

    @Test
    public void shouldDeleteById() {
        service.create(TOM_CAT);
        service.create(GARFIELD_CAT);

        assertThat(service.all().size()).isEqualTo(2);
        assertThat(TOM_CAT).isEqualTo(service.get(TOM_CAT.getId()));
        assertThat(GARFIELD_CAT).isEqualTo(service.get(GARFIELD_CAT.getId()));

        service.delete(TOM_CAT.getId());

        assertThat(service.all().size()).isEqualTo(1);
        assertThat(service.get(TOM_CAT.getId())).isNull();
        assertThat(GARFIELD_CAT).isEqualTo(service.get(GARFIELD_CAT.getId()));
    }

    @Test
    public void shouldFilterByNullAndReturnAll() {
        service.create(TOM_CAT);
        service.create(GARFIELD_CAT);
        assertThat(service.all().size()).isEqualTo(2);

        Collection<Cat> filtered = service.filterBy(null);

        assertThat(filtered.size()).isEqualTo(2);
        assertThat(filtered.contains(TOM_CAT)).isTrue();
        assertThat(filtered.contains(GARFIELD_CAT)).isTrue();
    }

    @Test
    public void shouldFilterByEmptyStringAndReturnAll() {
        service.create(TOM_CAT);
        service.create(GARFIELD_CAT);
        assertThat(service.all().size()).isEqualTo(2);

        Collection<Cat> filtered = service.filterBy("");

        assertThat(filtered.size()).isEqualTo(2);
        assertThat(filtered.contains(TOM_CAT)).isTrue();
        assertThat(filtered.contains(GARFIELD_CAT)).isTrue();
    }

    @Test
    public void shouldFilterByCommonDescriptionAndReturnAll() {
        service.create(TOM_CAT);
        service.create(GARFIELD_CAT);
        assertThat(service.all().size()).isEqualTo(2);

        Collection<Cat> filtered = service.filterBy("cat");

        assertThat(filtered.size()).isEqualTo(2);
        assertThat(filtered.contains(TOM_CAT)).isTrue();
        assertThat(filtered.contains(GARFIELD_CAT)).isTrue();
    }

    @Test
    public void shouldFilterAndReturnTomCat() {
        service.create(TOM_CAT);
        service.create(GARFIELD_CAT);
        assertThat(service.all().size()).isEqualTo(2);

        Collection<Cat> filteredByName = service.filterBy("Tom");
        assertThat(filteredByName.size()).isEqualTo(1);
        assertThat(filteredByName.contains(TOM_CAT)).isTrue();

        Collection<Cat> filteredByDescription = service.filterBy("Bob cat");
        assertThat(filteredByDescription.size()).isEqualTo(1);
        assertThat(filteredByDescription.contains(TOM_CAT)).isTrue();
    }

    @Test
    public void shouldFilterAndReturnGarfieldCat() {
        service.create(TOM_CAT);
        service.create(GARFIELD_CAT);
        assertThat(service.all().size()).isEqualTo(2);

        Collection<Cat> filteredByName = service.filterBy("Garfield");
        assertThat(filteredByName.size()).isEqualTo(1);
        assertThat(filteredByName.contains(GARFIELD_CAT)).isTrue();

        Collection<Cat> filteredByDescription = service.filterBy("Cool cat");
        assertThat(filteredByDescription.size()).isEqualTo(1);
        assertThat(filteredByDescription.contains(GARFIELD_CAT)).isTrue();
    }

}
