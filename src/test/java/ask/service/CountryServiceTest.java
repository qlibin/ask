package ask.service;

import ask.AskApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by alibin on 9/25/15.
 *
 * @author alibin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AskApplication.class)
@WebAppConfiguration
public class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Test
    public void testDetectCountry() throws Exception {

        String country = countryService.detectCountry("8.8.8.8");

        if (!country.equals("lv")) // in case of connectivity issues
            assertEquals("8.8.8.8 should be in us", "us", country);

        country = countryService.detectCountry("wrong ip");

        assertEquals("for wrong ip lv should be returned", "lv", country);

    }
}