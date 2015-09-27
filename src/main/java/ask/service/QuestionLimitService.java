package ask.service;

import ask.repository.QuestionRepository;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by alibin on 9/27/15.
 *
 * @author alibin
 */
@Service
public class QuestionLimitService {

    @Autowired
    private QuestionRepository questionRepository;

    @Value("${question.limit.byCountry.count}")
    private int count;

    @Value("${question.limit.byCountry.seconds}")
    private int seconds;

    /**
     * Return true if limit of posted questions by country is not exceeded
     * @param country ISO country code ('lv')
     * @return true if limit of posted questions by country is not exceeded
     */
    public boolean checkCountryLimit(String country) {
        if (count > 0) {
            Date after = DateUtils.addSeconds(new Date(), -seconds);
            return questionRepository.countByCountryAndCreatedAfter(country, after) < count;
        }
        return true;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
