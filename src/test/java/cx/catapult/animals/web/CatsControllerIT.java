package cx.catapult.animals.web;


import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.Collection;

import static cx.catapult.animals.web.CatsMapping.CATS_API_V1;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CatsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    private final Cat TOM_CAT = new Cat("Tom", "Bob cat");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + CATS_API_V1);
    }

    @Test
    public void shouldLoadAll() {
        Collection items = template.getForObject(getBaseUrl(), Collection.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void shouldLoadById() {
        final Cat created = create(new Cat("Test 1 name", "Test 1 description"));
        ResponseEntity<String> response = template.getForEntity(getBaseUrl() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void shouldCreate() {
        ResponseEntity<Cat> response = template.postForEntity(getBaseUrl(), TOM_CAT, Cat.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo(TOM_CAT.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(TOM_CAT.getDescription());
        assertThat(response.getBody().getGroup()).isEqualTo(TOM_CAT.getGroup());
    }

    @Test
    public void shouldUpdate() {
        final Cat created = create(new Cat("Test 2 name", "Test 2 description"));

        final Cat updatedObject = new Cat("Test 2 name - updated", "Test 2 description - updated");
        ResponseEntity<Cat> response = template.postForEntity(getBaseUrl() + "/" + created.getId(), updatedObject, Cat.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        final Cat updated = response.getBody();
        assertThat(updated.getId()).isEqualTo(created.getId());
        assertThat(updated.getName()).isEqualTo(updatedObject.getName());
        assertThat(updated.getDescription()).isEqualTo(updatedObject.getDescription());
        assertThat(updated.getGroup()).isEqualTo(updatedObject.getGroup());
    }

    @Test
    public void shouldDeleteById() {
        ResponseEntity<Cat> responseCreate = template.postForEntity(getBaseUrl(), TOM_CAT, Cat.class);
        assertThat(responseCreate.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final Cat created = responseCreate.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(TOM_CAT.getName());
        assertThat(created.getDescription()).isEqualTo(TOM_CAT.getDescription());
        assertThat(created.getGroup()).isEqualTo(TOM_CAT.getGroup());

        template.delete(getBaseUrl() + "/" + created.getId());

        ResponseEntity<String> response = template.getForEntity(getBaseUrl() + "/" + created.getId(), String.class);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void shouldFilterByEmptyStringAndReturnAll() {
        Collection allItems = template.getForObject(getBaseUrl(), Collection.class);
        assertThat(allItems.size()).isGreaterThanOrEqualTo(7);

        Collection filtered = template.getForObject(getBaseUrl() + "/filter?query=", Collection.class);
        assertThat(filtered.size()).isEqualTo(allItems.size());
    }

    @Test
    public void shouldFilterByCommonNameAndReturnFiltered() {
        ResponseEntity<Collection<Cat>> filteredResponseEntity = template.exchange(getBaseUrl() + "/filter?query=TIG", HttpMethod.GET, null, getCollectionOfCatsResponseType());
        assertThat(filteredResponseEntity.getBody()).isNotNull();

        Cat[] filteredArray = filteredResponseEntity.getBody().toArray(new Cat[0]);
        assertThat(filteredArray.length).isEqualTo(2);

        assertThat(filteredArray[0].getName()).isEqualTo("Tiger");
        assertThat(filteredArray[0].getDescription()).isEqualTo("Large cat");

        assertThat(filteredArray[1].getName()).isEqualTo("Tigger");
        assertThat(filteredArray[1].getDescription()).isEqualTo("Not a scary cat");
    }

    @Test
    public void shouldFilterByNameAndReturnGarfieldCat() {
        ResponseEntity<Collection<Cat>> filteredResponseEntity = template.exchange(getBaseUrl() + "/filter?query=garfield", HttpMethod.GET, null, getCollectionOfCatsResponseType());
        assertThat(filteredResponseEntity.getBody()).isNotNull();

        Cat[] filteredArray = filteredResponseEntity.getBody().toArray(new Cat[0]);
        assertThat(filteredArray.length).isEqualTo(1);

        assertThat(filteredArray[0].getName()).isEqualTo("Garfield");
        assertThat(filteredArray[0].getDescription()).isEqualTo("Lazy cat");
    }

    @Test
    public void shouldFilterByDescriptionAndReturnSmellyCat() {
        ResponseEntity<Collection<Cat>> filteredResponseEntity = template.exchange(getBaseUrl() + "/filter?query=Cat+with+friends", HttpMethod.GET, null, getCollectionOfCatsResponseType());
        assertThat(filteredResponseEntity.getBody()).isNotNull();

        Cat[] filteredArray = filteredResponseEntity.getBody().toArray(new Cat[0]);
        assertThat(filteredArray.length).isEqualTo(1);

        assertThat(filteredArray[0].getName()).isEqualTo("Smelly");
        assertThat(filteredArray[0].getDescription()).isEqualTo("Cat with friends");
    }

    @Test
    public void shouldFilterByNameAndDescriptionAndReturnFiltered() {
        ResponseEntity<Collection<Cat>> filteredResponseEntity = template.exchange(getBaseUrl() + "/filter?query=JERRY", HttpMethod.GET, null, getCollectionOfCatsResponseType());
        assertThat(filteredResponseEntity.getBody()).isNotNull();

        Cat[] filteredArray = filteredResponseEntity.getBody().toArray(new Cat[0]);
        assertThat(filteredArray.length).isEqualTo(2);

        assertThat(filteredArray[0].getName()).isEqualTo("Tom");
        assertThat(filteredArray[0].getDescription()).isEqualTo("Friend of Jerry");

        assertThat(filteredArray[1].getName()).isEqualTo("Jerry");
        assertThat(filteredArray[1].getDescription()).isEqualTo("Not really a cat");
    }

    private Cat create(Cat cat) {
        final Cat created = template.postForObject(getBaseUrl(), cat, Cat.class);
        assertThat(created.getId()).isNotEmpty();
        assertThat(created.getName()).isEqualTo(cat.getName());
        assertThat(created.getDescription()).isEqualTo(cat.getDescription());
        return created;
    }

    private String getBaseUrl() {
        return base.toString();
    }

    private ParameterizedTypeReference<Collection<Cat>> getCollectionOfCatsResponseType() {
        return new ParameterizedTypeReference<Collection<Cat>>() {
        };
    }
}
