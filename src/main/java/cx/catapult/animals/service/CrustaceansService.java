package cx.catapult.animals.service;

import cx.catapult.animals.domain.Crustacean;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class CrustaceansService extends BaseService<Crustacean> {

    @PostConstruct
    public void initialize() {
        this.create(new Crustacean("Sebastian", "Not so Friendly Lobster"));
        this.create(new Crustacean("Larry the Lobster", "Friendly Lobster"));
        this.create(new Crustacean("Ebirah", "Giant Lobster"));
    }
}
