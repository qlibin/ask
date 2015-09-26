package ask.controller

import ask.AskApplication
import ask.Fixture
import ask.domain.Question
import ask.repository.QuestionRepository
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
class QuestionsControllerPostTest extends Specification {

    @Value("\${local.server.port}")
    private int port

    private String baseUrl = "http://localhost"

    RestTemplate restTemplate = new TestRestTemplate()

    @Autowired
    QuestionRepository questionRepository

    @Autowired
    QuestionService questionService

    def "The api should accept questions"() {



        when: "the new question posted"

        def request = new QuestionsController.QuestionRequest()
        request.text = Fixture.testQuestionText
        def response = restTemplate.postForEntity(
                "$baseUrl:$port/api/questions",
                request, Question.class)
        def question = response.body



        then: "the response is OK and the question text is match posted"

        response.statusCode == HttpStatus.OK
        question.text == request.text



        cleanup: "the DB from the test question"

        questionRepository.deleteByText(request.text)

    }

}
