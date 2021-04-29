package cx.catapult.animals.api;

import cx.catapult.animals.domain.Group;
import lombok.Builder;
import lombok.Value;

import static cx.catapult.animals.domain.Group.INVERTEBRATE;

@Value
@Builder(toBuilder = true)
public class CreateCrustaceanRequest {
  String name;
  String description;
  @Builder.Default Group group = INVERTEBRATE;
}
