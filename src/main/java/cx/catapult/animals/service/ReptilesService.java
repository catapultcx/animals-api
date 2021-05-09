package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Reptile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ReptilesService extends BaseService<Reptile>{

    @PostConstruct
    public void initialize() {
        this.create(new Reptile("Leo", "Leo lizard"));
    }


    public Reptile update(String id, Reptile toSave){
        Reptile reptile = get(id);

      if(reptile != null){
          reptile.setDescription(toSave.getDescription());
          reptile.setName(toSave.getName());
      }else{
          toSave.setId(id);
          reptile = create(toSave);
      }

        return reptile;
    }
}
