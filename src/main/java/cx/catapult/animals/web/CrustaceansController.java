package cx.catapult.animals.web;

import cx.catapult.animals.api.request.CreateOrUpdateCrustaceanRequest;
import cx.catapult.animals.api.response.Crustacean;
import cx.catapult.animals.exception.DataNotFoundException;
import cx.catapult.animals.mapper.CrustaceanMapper;
import cx.catapult.animals.repository.CrustaceanRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/1/crustaceans", produces = MediaType.APPLICATION_JSON_VALUE)
public class CrustaceansController {

  private final CrustaceanRepository repository;
  private final CrustaceanMapper crustaceanMapper;

  @Operation(description = "Creates a new Crustacean")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Create Crustacean")})
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Crustacean create(
      @Valid @RequestBody CreateOrUpdateCrustaceanRequest createCrustaceanRequest) {
    return crustaceanMapper.toCrustacean(
        repository.save(crustaceanMapper.toPersistentCrustacean(createCrustaceanRequest)));
  }

  @Operation(description = "Get all Crustaceans")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Crustaceans Found"),
        @ApiResponse(responseCode = "404", description = "Crustaceans Not Found")
      })
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Crustacean> list() {
    return stream(repository.findAll().spliterator(), false)
        .map(crustaceanMapper::toCrustacean)
        .collect(toList());
  }

  @Operation(description = "Get a Crustacean by id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Crustacean Found"),
        @ApiResponse(responseCode = "404", description = "Crustacean Not Found")
      })
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Crustacean get(@PathVariable String id) {
    return repository
        .findById(id)
        .map(crustaceanMapper::toCrustacean)
        .orElseThrow(() -> new DataNotFoundException(Crustacean.class, id));
  }

  @Operation(description = "Delete a Crustacean by id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Crustacean Deleted"),
        @ApiResponse(responseCode = "404", description = "Crustacean Not Found")
      })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable String id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new DataNotFoundException(Crustacean.class, id);
    }
  }

  @Operation(description = "Updates a Crustacean")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Updated Crustacean")})
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Crustacean update(
      @PathVariable String id,
      @Valid @RequestBody CreateOrUpdateCrustaceanRequest updateCrustaceanRequest) {
    return repository
        .findById(id)
        .map(p -> crustaceanMapper.toPersistentCrustacean(updateCrustaceanRequest))
        .map(repository::save)
        .map(crustaceanMapper::toCrustacean)
        .orElseThrow(() -> new DataNotFoundException(Crustacean.class, id));
  }
}
