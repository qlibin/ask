package ask.controller;

import ask.domain.Question;
import ask.service.BlackListService;
import ask.service.CountryService;
import ask.service.QuestionService;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public HttpEntity<PagedResources<Question>> questionList(
            @PageableDefault(sort = "lastUpdated", direction = Sort.Direction.DESC, value = 20)
            Pageable pageable,
            PagedResourcesAssembler assembler)
    {
        Page<Question> questions = questionService.listQuestions(pageable);
        return new ResponseEntity<>(assembler.toResource(questions), HttpStatus.OK);
    }



    @RequestMapping(method = RequestMethod.GET, value = "/questions/{country:\\w{2}}")
    public HttpEntity<PagedResources<Question>> questionListByCountry(
            @PathVariable String country,
            @PageableDefault(sort = "lastUpdated", direction = Sort.Direction.DESC, value = 20)
            Pageable pageable,
            PagedResourcesAssembler assembler)
    {
        Page<Question> questions = questionService.listQuestions(country, pageable);
        return new ResponseEntity<>(assembler.toResource(questions), HttpStatus.OK);
    }



    @RequestMapping(method = RequestMethod.GET, value = "/questions/{id:.{16,}}")
    public HttpEntity<Question> getQuestion(@PathVariable String id) {
        Question question = questionService.getQuestion(id);
        if (question == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(question, HttpStatus.OK);
        }
    }



    @RequestMapping(method = RequestMethod.POST, value = "/questions")
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionRequest questionRequest, HttpServletRequest httpServletRequest) {
        return getQuestionResponseEntity(new Question(), questionRequest, httpServletRequest);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable String id, @RequestBody QuestionRequest questionRequest, HttpServletRequest httpServletRequest) {
        Question question = questionService.getQuestion(id);
        if (question == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return getQuestionResponseEntity(question, questionRequest, httpServletRequest);
    }

    private ResponseEntity<Question> getQuestionResponseEntity(Question question, @RequestBody QuestionRequest questionRequest, HttpServletRequest httpServletRequest) {
        if (questionRequest.text == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!blackListService.check(questionRequest.text)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        question.setText(questionRequest.text);
        question.setCountry(countryService.detectCountry(httpServletRequest.getRemoteHost()));
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
