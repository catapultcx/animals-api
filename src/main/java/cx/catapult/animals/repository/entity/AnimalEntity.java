package cx.catapult.animals.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "animal_tbl")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long id;
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;
    @Column(name = "DESCRIPTION", nullable = false, unique = true)
    private String description;
    @Column(name = "ANIMAL_TYPE", nullable = false, unique = true)
    private String animalType;
    @Column(name = "GROUP_NAME", nullable = false, unique = true)
    private String group;
    @Column(name = "CREATED_DATE", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;
    @Column(name = "UPDATED_DATE", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedDate;



}
