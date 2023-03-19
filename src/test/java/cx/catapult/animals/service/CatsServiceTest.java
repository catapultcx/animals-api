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
    public void shouldCreate() {
        Cat actual = service.create(TOM_CAT);

        assertThat(actual).isEqualTo(TOM_CAT);
        assertThat(actual.getName()).isEqualTo(TOM_CAT.getName());
        assertThat(actual.getDescription()).isEqualTo(TOM_CAT.getDescription());
        assertThat(actual.getGroup()).isEqualTo(TOM_CAT.getGroup());
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

        Cat actual = service.get(TOM_CAT.getId());

        assertThat(actual).isEqualTo(TOM_CAT);
        assertThat(actual.getName()).isEqualTo(TOM_CAT.getName());
        assertThat(actual.getDescription()).isEqualTo(TOM_CAT.getDescription());
        assertThat(actual.getGroup()).isEqualTo(TOM_CAT.getGroup());
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
}
