package cx.catapult.animals.mapper;

import cx.catapult.animals.api.CreateCrustaceanRequest;
import cx.catapult.animals.api.response.Crustacean;
import cx.catapult.animals.domain.PersistentCrustacean;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CrustaceanMapper {

  Crustacean toCrustacean(PersistentCrustacean persistentCrustacean);

  PersistentCrustacean toPersistentCrustacean(CreateCrustaceanRequest createCrustaceanRequest);
}
