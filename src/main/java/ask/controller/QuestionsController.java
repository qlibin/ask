package ask.controller;

import ask.domain.Question;
import ask.service.BlackListService;
import ask.service.CountryService;
import ask.service.QuestionService;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by alibin on 9/24/15.
 *
 * @author alibin
 */
@RestController
@RequestMapping("/api")
public class QuestionsController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private BlackListService blackListService;

    @RequestMapping(method = RequestMethod.GET, value = "/questions")
    public List<Question> questionList() {
        return questionService.listValidQuestions();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/questions/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable String id) {
        Question question = questionService.getQuestion(id);
        if (question.getValid()) {
            return new ResponseEntity<>(question, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{country}/questions")
    public List<Question> questionListByCountry(@PathVariable String country) {
        return questionService.listValidQuestions(country);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/questions")
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionRequest questionRequest, HttpServletRequest httpServletRequest) {
        if (questionRequest.text == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Question question = new Question();
        question.setText(questionRequest.text);
        question.setCountry(countryService.detectCountry(httpServletRequest.getRemoteHost()));
        question.setValid(blackListService.check(questionRequest.text));
        // todo: check if limit for the country is exceeded
        questionService.saveQuestion(question);
        log.debug("New question: {}", question);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    public static class QuestionRequest {
        public String text;

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("text", text)
                    .toString();
        }
    }
}
