package cx.catapult.animals.service;

import cx.catapult.animals.domain.Arachnid;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.BaseService;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class ArachnidsService extends BaseService<Arachnid> {

    @PostConstruct
    public void initialize() {
        this.create(new Arachnid("Shelob", "Lord of the Rings"));
        this.create(new Arachnid("Gohma", "Legend of Zelda"));
        this.create(new Arachnid("Spider Queen of Metebelis Three", "Doctor Who"));
        this.create(new Arachnid("Miss Spider", "James and the Giant Peach"));
        this.create(new Arachnid("Anansi", "African"));
    }

}
