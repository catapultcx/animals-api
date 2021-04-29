package cx.catapult.animals.web;

import cx.catapult.animals.api.CreateCrustaceanRequest;
import cx.catapult.animals.api.response.Crustacean;
import cx.catapult.animals.mapper.CrustaceanMapper;
import cx.catapult.animals.repository.CrustaceanRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/1/crustaceans", produces = MediaType.APPLICATION_JSON_VALUE)
public class CrustaceansController {

  private final CrustaceanRepository repository;
  private final CrustaceanMapper crustaceanMapper;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Crustacean create(@Valid @RequestBody CreateCrustaceanRequest createCrustaceanRequest) {
    return crustaceanMapper.toCrustacean(
        repository.save(crustaceanMapper.toPersistentCrustacean(createCrustaceanRequest)));
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Crustacean> list() {
    return stream(repository.findAll().spliterator(), false)
        .map(crustaceanMapper::toCrustacean)
        .collect(toList());
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Optional<Crustacean> get(@PathVariable String id) {
    return repository.findById(id).map(crustaceanMapper::toCrustacean);
  }
}
