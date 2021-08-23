package cx.catapult.animals.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.entity.CatEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface CatsMapper {

  List<CatEntity> getCatEntities(final List<Cat> cats);

  CatEntity getCatEntity(final Cat cat);

  List<Cat> getCats(final Iterable<CatEntity> cats);

  Cat getCat(final CatEntity cat);
}
