package ask.service;

import ask.domain.Question;
import ask.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            @CacheEvict(value = "listQuestions"),
            @CacheEvict(value = "listQuestionsByCountry", key = "#a0.country")
    })
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    // todo: fix the cache eviction
    @Caching(evict = {
            @CacheEvict(value = "questionsById", key = "#a0.id"),
            @CacheEvict(value = "listQuestions", allEntries = true),
            @CacheEvict(value = "listQuestionsByCountry", allEntries = true)
    })
    public void deleteQuestion(Question question) {
        if (question != null) {
            questionRepository.delete(question);
        }
    }

    public void deleteQuestion(String id) {
        Question question = getQuestion(id);
        deleteQuestion(question);
    }

    @Cacheable(value = "listQuestions")
    public Page<Question> listQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Cacheable(value = "listQuestionsByCountry")
    public Page<Question> listQuestions(String country, Pageable pageable) {
        return questionRepository.findAllByCountry(country, pageable);
    }

}
