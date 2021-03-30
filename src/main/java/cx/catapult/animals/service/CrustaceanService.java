package cx.catapult.animals.service;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.repo.CrustaceanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CrustaceanService extends BaseService<Crustacean> {

    private final CrustaceanRepository crustaceanRepository;

    @Autowired
    private CrustaceanService(CrustaceanRepository crustaceanRepository){
        this.crustaceanRepository = crustaceanRepository;
    }

    @Transactional
    public Crustacean create(Crustacean crustacean) {
//        4.6 6 Persist data in DB
//            2. Must be keep existing memory back end (BaseService)
        super.create(crustacean); // Design issue you can have separate memory storage class, cant test in unit test
        Crustacean crustaceanStored = crustaceanRepository.save(crustacean);
        return crustaceanStored;
    }
}
