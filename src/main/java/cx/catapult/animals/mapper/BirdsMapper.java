package cx.catapult.animals.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.entity.BirdEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface BirdsMapper {

  List<BirdEntity> getBirdEntities(final List<Bird> birds);

  BirdEntity getBirdEntity(final Bird bird);

  List<Bird> getBirds(final Iterable<BirdEntity> birds);

  Bird getBird(final BirdEntity bird);
}
