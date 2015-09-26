package ask.controller

import ask.AskApplication
import ask.Fixture
import ask.domain.BadWord
import ask.domain.Question
import ask.repository.BadWordRepository
import ask.repository.QuestionRepository
import ask.service.BlackListService
import ask.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

/**
 * Created by alibin on 9/25/15.
 * @author alibin
 */
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = AskApplication.class)
@WebAppConfiguration
@IntegrationTest(["server.port=0", "management.port=0"])
class QuestionsControllerPostInapropriateTest extends Specification {

    @Value("\${local.server.port}")
    private int port

    RestTemplate restTemplate = new TestRestTemplate()

    @Autowired
    QuestionRepository questionRepository

    @Autowired
    BadWordRepository badWordRepository

    @Autowired
    QuestionService questionService

    @Autowired
    BlackListService blackListService

    def "Question with blacklisted words should be rejected"() {



        given: "there is a blacklisted word"

        BadWord badWord = blackListService.add(Fixture.badWord)



        when: "a question with the blacklisted word posted"

        def request = new QuestionsController.QuestionRequest()
        request.text = Fixture.testInappropriateQuestionText
        def response = restTemplate.postForEntity(
                "$Fixture.baseUrl:$port/api/questions",
                request, Question.class)



        then: "the question is rejected"

        response.statusCode == HttpStatus.BAD_REQUEST



        cleanup: "the DB from the bad word"

        badWordRepository.delete(badWord.getId())
        questionRepository.deleteByText(request.text)


    }


}
