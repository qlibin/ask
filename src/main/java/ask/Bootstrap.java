package ask;

import ask.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alibin on 9/25/15.
 *
 * @author alibin
 */
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BlackListService blackListService;

    @Autowired
    private QuestionLimitSettings questionLimitSettings;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        initBadWords();

    }

    private void initBadWords() {

        // init black list of words

        List<String> badWords = questionLimitSettings.getBlacklist();
        if (badWords == null || badWords.isEmpty()) {
            badWords = Arrays.asList(
                    "fuck", "shit"
            );
        }
        badWords.stream().forEach(s -> blackListService.add(s));

    }
}
