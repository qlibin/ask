package ask.service;

import ask.AskApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by alibin on 9/24/15.
 *
 * @author alibin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AskApplication.class)
@WebAppConfiguration
public class BlackListServiceTest {

    @Autowired
    private BlackListService blackListService;

    @Test
    public void testCheck() throws Exception {

        assertTrue("Check should pass", blackListService.check("some text"));

    }

}