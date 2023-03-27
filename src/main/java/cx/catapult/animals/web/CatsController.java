package cx.catapult.animals.web;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.exception.AnimalNotFoundException;
import cx.catapult.animals.service.CatsService;

@RestController
@RequestMapping(path = "/api/1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class CatsController {

	@Autowired
	private CatsService service;

	@GetMapping(value = "", produces = "application/json")
	public @ResponseBody Collection<Cat> all() {
		return service.all();
	}

	@GetMapping(value = "/{id}")
	public @ResponseBody Cat get(@PathVariable String id) {
		try {
			return service.get(id);
		} catch (AnimalNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(value = "/search", produces = "application/json")
	public @ResponseBody Collection<Cat> search(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "description", required = false) String description) {

		if (name != null) {
			return service.getByName(name);
		}

		if (description != null) {
			return service.getByDescription(description);
		}

		return service.all();
	}

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Cat create(@RequestBody Cat cat) {
		try {
			return service.create(cat);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		try {
			service.delete(id);
		} catch (AnimalNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Cat update(@PathVariable String id, @RequestBody Cat cat) {
		try {
			return service.update(id, cat);
		} catch (AnimalNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity handleFormPutAndDelete(@PathVariable String id, @RequestParam("_method") String method,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "description", required = false) String description) {
		try {
			if ("PUT".equals(method)) {
				service.update(id, new Cat(name, description));
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(URI.create("http://localhost:3000/cats"));
				return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
			} else if ("DELETE".equals(method)) {
				service.delete(id);
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(URI.create("http://localhost:3000/cats"));
				return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
			}
		} catch (AnimalNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		return null;
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity handleFormPost(@RequestParam("name") String name,
			@RequestParam("description") String description) {
		try {
			service.create(new Cat(name, description));
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("http://localhost:3000/cats"));
			return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
