package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.util.TestUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CatsControllerIT {

    @Value("${local.server.port}")
    private int port;

    private URL base;

    private Cat cat = new Cat("Tom", "Bob cat");

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
	this.base = new URL(String.format("http://localhost:%s/api/1/cats", port));
    }

    @Test
    public void createShouldWork() throws Exception {
	ResponseEntity<Cat> response = template.postForEntity(base.toString(), cat, Cat.class);
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	assertThat(response.getBody().getId()).isNotEmpty();
	assertThat(response.getBody().getName()).isEqualTo(cat.getName());
	assertThat(response.getBody().getDescription()).isEqualTo(cat.getDescription());
	assertThat(response.getBody().getGroup()).isEqualTo(cat.getGroup());
    }

    @Test
    public void createBadRequestShouldWork() throws Exception {
	ResponseEntity<Cat> response = template.postForEntity(base.toString(), Cat.builder().build(), Cat.class);
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void allShouldWork() throws Exception {
	Collection<?> items = template.getForObject(base.toString(), Collection.class);
	assertThat(items.size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getShouldWork() throws Exception {
	Cat created = create("Test 1");
	ResponseEntity<String> response = template.getForEntity(base.toString().concat("/").concat(created.getId()),
		String.class);
	assertThat(response.getBody()).isNotNull().isNotEmpty().isEqualTo(TestUtil.javaToJson(created));
    }

    @Test
    public void getThatDoesNotExistShouldWork() throws Exception {
	ResponseEntity<String> response = template.getForEntity(base.toString().concat("/NotExists"), String.class);
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateShouldWork() throws Exception {
	final Cat created = create("Test 1");
	final Cat updated = created.toBuilder().description("American Shorthair").build();
	final HttpHeaders requestHeaders = new HttpHeaders();
	requestHeaders.setContentType(MediaType.APPLICATION_JSON);
	final ResponseEntity<Void> response = template.exchange(base.toString(), HttpMethod.PUT,
		new HttpEntity<>(TestUtil.javaToJson(updated), requestHeaders), Void.class);
	assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT);
	ResponseEntity<String> responseGet = template.getForEntity(base.toString().concat("/").concat(updated.getId()),
		String.class);
	assertThat(responseGet.getBody()).isNotNull().isNotEmpty().isEqualTo(TestUtil.javaToJson(updated));
    }

    @Test
    public void updateThatDoesNotExistShouldWork() throws Exception {
	final Cat notExist = cat.toBuilder().id("NotExist").build();
	final HttpHeaders requestHeaders = new HttpHeaders();
	requestHeaders.setContentType(MediaType.APPLICATION_JSON);
	final ResponseEntity<Void> response = template.exchange(base.toString(), HttpMethod.PUT,
		new HttpEntity<>(TestUtil.javaToJson(notExist), requestHeaders), Void.class);
	assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void removeShouldWork() throws Exception {
	final Cat created = create("Test 1");
	final ResponseEntity<Void> response = template.exchange(base.toString().concat("/").concat(created.getId()),
		HttpMethod.DELETE, null, Void.class);
	assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void removeThatDoesNotExistShouldWork() throws Exception {
	final ResponseEntity<Void> response = template.exchange(base.toString().concat("/NotExist"), HttpMethod.DELETE,
		null, Void.class);
	assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    private Cat create(String name) {
	Cat created = template.postForObject(base.toString(), new Cat(name, name), Cat.class);
	assertThat(created.getId()).isNotNull().isNotEmpty();
	assertThat(created.getName()).isNotNull().isEqualTo(name);
	assertThat(created.getDescription()).isNotNull().isEqualTo(name);
	return created;
    }
}
