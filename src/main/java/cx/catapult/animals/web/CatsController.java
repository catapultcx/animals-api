package cx.catapult.animals.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

		// Return all cats if no search criteria is specified
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

}
