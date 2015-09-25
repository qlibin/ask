package ask.repository;

import ask.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alibin on 9/24/15.
 *
 * @author alibin
 */
@Component
public interface QuestionRepository extends MongoRepository<Question, String> {

    List<Question> findAllByIsValid(boolean isValid);

    List<Question> findAllByCountry(String country);

    List<Question> findAllByCountryAndIsValid(String country, boolean isValid);

}
