package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAmphibian;
import cx.catapult.animals.domain.Group;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class AmphibianService extends BaseService<BaseAmphibian> {
    private final StorageService service;

    public AmphibianService(final StorageService service) {
        super(service);
        this.service = service;
        service.setGroup(Group.AMPHIBIAN);
    }

    @PostConstruct
    public void initialize() {
        this.create(new BaseAmphibian("Lizzie", "The friendly sand gecko"));
    }

}
