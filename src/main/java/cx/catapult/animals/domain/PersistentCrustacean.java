package cx.catapult.animals.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@SuperBuilder
@Entity
@Table(name = "Crustaceans")
public class PersistentCrustacean extends BaseAnimal {

  public PersistentCrustacean() {
    super();
  }
}
