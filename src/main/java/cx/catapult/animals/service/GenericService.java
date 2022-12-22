package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Generic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GenericService extends BaseService<Generic> {

    @PostConstruct
    public void initialize() {
        this.create(new Generic("Roy", "Barking canine"));
        this.create(new Generic("Ermentrude", "Happy bovine"));
        this.create(new Generic("Larry", "Excited camelid"));
        this.create(new Generic("Tony", "Tiny testudinid"));
        this.create(new Generic("Boris", "A mean arachnid"));
    }

}
