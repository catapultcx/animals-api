package cx.catapult.animals.web;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.CatsService;
import cx.catapult.animals.validator.CatValidator;

@RestController
@RequestMapping(path = "/api/1/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsController {

    @Autowired
    private CatsService service;

    @Autowired
    private CatValidator catValidator;

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody Collection<Cat> all() {
	return service.all();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<Cat> get(@PathVariable String id) {
	final Cat cat = service.get(id);
	return Objects.isNull(cat) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(cat);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> create(@RequestBody Cat cat, final BindingResult result) {
	catValidator.validate(cat, result);
	if (result.hasErrors()) {
	    return ResponseEntity.badRequest().body(result.getFieldError());
	} else {
	    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(cat));
	}
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> update(@RequestBody Cat cat, final BindingResult result) {
	return Optional.ofNullable(service.get(cat.getId())).map(entity -> updateEntity(cat, result))
		.orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<Object> delete(@PathVariable String id) {
	return Optional.ofNullable(service.remove(id)).map(entity -> ResponseEntity.noContent().build())
		.orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<Object> updateEntity(Cat cat, final BindingResult result) {
	catValidator.validate(cat, result);
	if (result.hasErrors()) {
	    return ResponseEntity.badRequest().body(result.getFieldError());
	} else {
	    service.update(cat);
	    return ResponseEntity.noContent().build();
	}
    }
}
