package cx.catapult.animals.api.request;

import cx.catapult.animals.domain.Group;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Size;

import static cx.catapult.animals.domain.Group.INVERTEBRATE;

@Value
@Builder(toBuilder = true)
public class CreateOrUpdateCrustaceanRequest {

  @Size(max = 25)
  String name;

  @Size(max = 50)
  String description;

  Group group = INVERTEBRATE;
}
