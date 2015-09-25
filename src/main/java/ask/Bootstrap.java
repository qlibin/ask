package ask;

import ask.repository.BadWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by alibin on 9/25/15.
 *
 * @author alibin
 */
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BadWordRepository badWordRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        initBadWords();

    }

    private void initBadWords() {

        // todo: init black list of words

    }
}
