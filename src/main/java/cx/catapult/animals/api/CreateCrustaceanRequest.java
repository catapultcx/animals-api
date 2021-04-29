package cx.catapult.animals.api;

import cx.catapult.animals.domain.Group;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Size;

import static cx.catapult.animals.domain.Group.INVERTEBRATE;

@Value
@Builder(toBuilder = true)
public class CreateCrustaceanRequest {

  @Size(max = 25)
  String name;

  @Size(max = 50)
  String description;

  @Builder.Default Group group = INVERTEBRATE;
}
