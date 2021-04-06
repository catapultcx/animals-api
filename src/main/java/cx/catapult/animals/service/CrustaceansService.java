package cx.catapult.animals.service;

import cx.catapult.animals.domain.Crustacean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CrustaceansService extends BaseService<Crustacean> {

    @PostConstruct
    public void initialize() {

    }

}
