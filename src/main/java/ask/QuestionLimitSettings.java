package ask;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alibin on 9/27/15.
 *
 * @author alibin
 */
@Component
@ConfigurationProperties(prefix="question.limit")
public class QuestionLimitSettings {

    private List<String> blacklist = new ArrayList<>();

    public List<String> getBlacklist() {
        return blacklist;
    }

}
