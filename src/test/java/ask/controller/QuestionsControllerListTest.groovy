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
class QuestionsControllerListTest extends Specification {

    @Value("\${local.server.port}")
    private int port

    RestTemplate restTemplate = new TestRestTemplate()

    @Autowired
    QuestionRepository questionRepository

    @Autowired
    QuestionService questionService

    def "The api should be reachable"() {



        when: "the questions page accessed"

        def response = restTemplate.getForEntity(
                "$Fixture.baseUrl:$port/api/questions", String.class)



        then: "the response is OK and the body is not empty"

        response.statusCode == HttpStatus.OK
        response.body != ''

    }


    def "The api should return list of questions"() {



        given: "there are some questions in DB"
        def questions = [
            [text: "1" + Fixture.testQuestionText, country: "lv"],
            [text: "2" + Fixture.testQuestionText, country: "lv"],
            [text: "3" + Fixture.testQuestionText, country: "lv"],
            [text: "4" + Fixture.testQuestionText, country: "lv"],
            [text: "5" + Fixture.testQuestionText, country: "us"],
            [text: "6" + Fixture.testQuestionText, country: "us"],
            [text: "7" + Fixture.testQuestionText, country: "us"]
        ]
        questions.each {
            Question question = new Question()
            question.text = it.text
            question.country = it.country
            question = questionService.saveQuestion(question)
            it.id = question.id
        }



        when: "the questions page accessed"

        def response = restTemplate.getForEntity(
                "$Fixture.baseUrl:$port/api/questions", String.class)


        then: "the response is OK and the questions are in the response"

        response.statusCode == HttpStatus.OK
        questions.every {response.body.contains(it.text)}



        when: "the questions from lv are requested"

        def responseLv = restTemplate.getForEntity(
                "$Fixture.baseUrl:$port/api/questions/lv", String.class)



        then: "the response is OK and the questions from lv are in the response and none from us"

        responseLv.statusCode == HttpStatus.OK
        questions.findAll { it.country == 'lv' }.every { responseLv.body.contains(it.text) }
        questions.findAll { it.country == 'us' }.every { !responseLv.body.contains(it.text) }



        cleanup: "test questions"
        questions.each {
            questionRepository.delete(it.id)
        }

    }


}
