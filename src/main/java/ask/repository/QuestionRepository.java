package ask.repository;

import ask.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by alibin on 9/24/15.
 *
 * @author alibin
 */
@Component
public interface QuestionRepository extends MongoRepository<Question, String> {

    Page<Question> findAllByCountry(String country, Pageable pageable);

    Long deleteByText(String text);

    Long countByCountryAndCreatedAfter(String country, Date date);

}
