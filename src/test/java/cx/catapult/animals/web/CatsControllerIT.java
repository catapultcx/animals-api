package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.Collection;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cx.catapult.animals.domain.Cat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CatsControllerIT {
	@LocalServerPort
	private int port;

	private URL base;

	private Cat cat = new Cat("Tom", "Bob cat");

	@Autowired
	private TestRestTemplate template;

	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/api/1/cats");
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
	public void createFormShouldWork() throws Exception {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("name", "Fluffy");
		formData.add("description", "Persian cat");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

		ResponseEntity<Cat> response = template.postForEntity(base.toString() + "/form", request, Cat.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SEE_OTHER);
	}

	@Test
	public void createShouldFail() throws Exception {
		JSONObject json = new JSONObject();
		json.put("description", "Cat without name");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

		ResponseEntity<String> response = template.postForEntity(base.toString(), request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void allShouldWork() throws Exception {
		Collection items = template.getForObject(base.toString(), Collection.class);
		assertThat(items.size()).isGreaterThanOrEqualTo(7);
	}

	@Test
	public void getShouldWork() throws Exception {
		Cat created = create("Test 1");
		ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + created.getId(), String.class);
		assertThat(response.getBody()).isNotEmpty();
	}

	@Test
	public void getShouldFail() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + "invalid id", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void deleteShouldWork() throws Exception {
		Cat created = create("Test 2");
		ResponseEntity<Void> response = template.exchange(base.toString() + "/" + created.getId(),
				org.springframework.http.HttpMethod.DELETE, null, Void.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	public void deleteFormShouldWork() throws Exception {
		Cat created = create("Test Cat");

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("_method", "DELETE");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ResponseEntity<Cat> response = template.exchange(base.toString() + "/form/" + created.getId(), HttpMethod.POST,
				request, Cat.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SEE_OTHER);
	}

	@Test
	public void deleteShouldFail() throws Exception {
		ResponseEntity<Void> response = template.exchange(base.toString() + "/" + "invalid id",
				org.springframework.http.HttpMethod.DELETE, null, Void.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void updateShouldWork() throws Exception {
		Cat catToUpdate = create("Test 3");
		catToUpdate.setName("Updated cat");
		catToUpdate.setDescription("Updated description");
		ResponseEntity<Cat> response = template.exchange(base.toString() + "/" + catToUpdate.getId(), HttpMethod.PUT,
				new HttpEntity<>(catToUpdate), Cat.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getName()).isEqualTo(catToUpdate.getName());
		assertThat(response.getBody().getDescription()).isEqualTo(catToUpdate.getDescription());
	}

	@Test
	public void updateFormShouldWork() throws Exception {
		Cat created = create("Test Cat");

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("name", "Updated Cat");
		map.add("description", "Updated Description");
		map.add("_method", "PUT");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ResponseEntity<Cat> response = template.exchange(base.toString() + "/form/" + created.getId(), HttpMethod.POST,
				request, Cat.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SEE_OTHER);
	}

	@Test
	public void updateShouldFail() throws Exception {
		Cat catToUpdate = create("Test 4");
		catToUpdate.setId("invalid id");
		ResponseEntity<Void> response = template.exchange(base.toString() + "/" + catToUpdate.getId(), HttpMethod.PUT,
				new HttpEntity<>(catToUpdate), Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	Cat create(String name) {
		Cat created = template.postForObject(base.toString(), new Cat(name, name), Cat.class);
		assertThat(created.getId()).isNotEmpty();
		assertThat(created.getName()).isEqualTo(name);
		return created;
	}
}
