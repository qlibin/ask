package ask.repository;

import ask.domain.BadWord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by alibin on 9/24/15.
 *
 * @author alibin
 */
@Component
public interface BadWordRepository extends MongoRepository<BadWord, String> {

    BadWord findByWord(String word);

}
