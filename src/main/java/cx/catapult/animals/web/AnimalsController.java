package cx.catapult.animals.web;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.service.AnimalsService;

@RestController
@RequestMapping(path = "/api/1/animals", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalsController {

	@Autowired
	private AnimalsService service;

	@GetMapping(value = "/{id}")
	public @ResponseBody BaseAnimal get(@PathVariable String id) {
		return service.get(id);
	}

	@GetMapping
	public @ResponseBody Collection<BaseAnimal> getByName(@RequestParam String name) {
		return service.getByName(name);
	}
}
