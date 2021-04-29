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
}
