package cx.catapult.animals.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("arachnid")
public class ArachnidEntity extends AnimalEntity {
}
