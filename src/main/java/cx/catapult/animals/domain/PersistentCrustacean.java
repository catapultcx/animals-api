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

  public PersistentCrustacean(String name, String description) {
    super(name, description, Group.INVERTEBRATE);
  }

  public PersistentCrustacean(String id, String name, String description) {
    super(id, name, description, Group.INVERTEBRATE);
  }
}
