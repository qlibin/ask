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
class QuestionsControllerGetTest extends Specification {

    @Value("\${local.server.port}")
    private int port

    RestTemplate restTemplate = new TestRestTemplate()

    @Autowired
    QuestionRepository questionRepository

    @Autowired
    QuestionService questionService

    def "Saved question should be available via GET"() {



        given: "the new question posted"

        def request = new QuestionsController.QuestionRequest()
        request.text = Fixture.testQuestionText
        def response = restTemplate.postForEntity(
                "$Fixture.baseUrl:$port/api/questions",
                request, Question.class)



        when: "the posted question requested by id"

        def question = response.body
        def secondResponse = restTemplate.getForEntity(
                "$Fixture.baseUrl:$port/api/questions/{id}", Question.class, question.id)



        then: "the response is OK and the question text is match posted"

        secondResponse.statusCode == HttpStatus.OK
        secondResponse.body.text == request.text



        when: "the posted question deleted and requested via api"

        questionService.deleteQuestion(question)

        def thirdResponse = restTemplate.getForEntity(
                "$Fixture.baseUrl:$port/api/questions/{id}", Question.class, question.id)



        then: "it should not be found"

        thirdResponse.statusCode == HttpStatus.NOT_FOUND



        cleanup: "the DB from the test question"

        questionRepository.deleteByText(request.text)


    }


}
