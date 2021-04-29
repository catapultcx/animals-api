package cx.catapult.animals.service;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import cx.catapult.animals.domain.Crustacean;

@Service
public class CrustaceansService extends BaseService<Crustacean> {

	@PostConstruct
	public void initialize() {
		this.create(new Crustacean("Crusty", "Crusty the crab"));
		this.create(new Crustacean("Libby", "Libby the Lobster"));
		this.create(new Crustacean("Paula", "Paula the Prawn"));
	}
}
