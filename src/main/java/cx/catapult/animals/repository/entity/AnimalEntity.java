package cx.catapult.animals.repository.entity;

import cx.catapult.animals.domain.Family;
import cx.catapult.animals.domain.Group;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Setter
@Getter
@Table(name = "animals")
public class AnimalEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "group_name")
    private Group group;

    @Column(name="family_name")
    private Family family;

}
