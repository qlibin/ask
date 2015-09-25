package ask.service;

import ask.domain.Question;
import ask.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by alibin on 9/25/15.
 *
 * @author alibin
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Cacheable(value = "questionsById")
    public Question getQuestion(String id) {
        if (id == null) {
            return null;
        }
        return questionRepository.findOne(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "questionsById", key = "#a0.id"),
            @CacheEvict(value = "listAllQuestions"),
            @CacheEvict(value = "listAllQuestionsByCountry", key = "#a0.country"),
            @CacheEvict(value = "listValidQuestions")
    })
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void validateAndSaveQuestion(Question question) {
//        Country
    }

    @Caching(evict = {
            @CacheEvict(value = "questionsById", key = "#a0.id"),
            @CacheEvict(value = "listAllQuestions"),
            @CacheEvict(value = "listAllQuestionsByCountry", key = "#a0.country"),
            @CacheEvict(value = "listValidQuestions")
    })
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }

    @Cacheable(value = "listAllQuestions")
    public List<Question> listAllQuestions() {
        return questionRepository.findAll();
    }

    @Cacheable(value = "listAllQuestionsByCountry")
    public List<Question> listAllQuestions(String country) {
        return questionRepository.findAllByCountry(country);
    }

    @Cacheable(value = "listValidQuestions")
    public List<Question> listValidQuestions() {
        return questionRepository.findAllByIsValid(true);
    }

    @Cacheable(value = "listValidQuestionsByCountry")
    public List<Question> listValidQuestions(String country) {
        return questionRepository.findAllByCountryAndIsValid(country, true);
    }

}
