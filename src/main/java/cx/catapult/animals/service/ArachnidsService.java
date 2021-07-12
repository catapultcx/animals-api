package cx.catapult.animals.service;

import cx.catapult.animals.domain.Arachnid;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ArachnidsService extends BaseService<Arachnid> {

  @PostConstruct
  public void initialize() {
    this.create(new Arachnid("T", "Friend of Jerry"));
    this.create(new Arachnid("J", "Not really a cat"));
    this.create(new Arachnid("B", "Furry cat"));
    this.create(new Arachnid("S", "Cat with friends"));
    this.create(new Arachnid("T", "Large cat"));
  }
}
