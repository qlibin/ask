package ask.controller

import ask.AskApplication
import ask.Fixture
import ask.domain.Question
import ask.repository.QuestionRepository
import ask.service.QuestionLimitService
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
class QuestionsControllerPostOutOfLimitTest extends Specification {

    @Value("\${local.server.port}")
    private int port

    RestTemplate restTemplate = new TestRestTemplate()

    @Autowired
    QuestionRepository questionRepository

    @Autowired
    QuestionLimitService questionLimitService


    def "Out of country limit questions should be rejected"() {



        given: "limit for a country is 1 question per 10 sec"

        questionLimitService.count = 1
        questionLimitService.seconds = 10



        when: "a first question posted"

        def request = new QuestionsController.QuestionRequest()
        request.text = Fixture.testQuestionText
        def response = restTemplate.postForEntity(
                "$Fixture.baseUrl:$port/api/questions",
                request, Question.class)



        then: "the question is accepted"

        response.statusCode == HttpStatus.OK
        response.body.text == request.text




        when: "a second question immediately posted within a limit"

        def secondRequest = new QuestionsController.QuestionRequest()
        secondRequest.text = Fixture.testQuestionText
        def secondResponse = restTemplate.postForEntity(
                "$Fixture.baseUrl:$port/api/questions",
                request, String.class)



        then: "the question is rejected"

        secondResponse.statusCode == HttpStatus.BAD_REQUEST
        secondResponse.body.contains("Country limit exceeded")


        cleanup: "the DB from the bad word"

        questionRepository.deleteByText(request.text)


    }


}
