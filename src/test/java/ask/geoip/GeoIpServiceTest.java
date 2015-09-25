package ask.geoip;

import ask.AskApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

/**
 * Created by alibin on 9/25/15.
 *
 * @author alibin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AskApplication.class)
@WebAppConfiguration
public class GeoIpServiceTest {

    @Autowired
    private GeoIpService geoIpService;

    @Test
    public void testGetGeoIpData() throws Exception {
        GeoIpResponse data = geoIpService.getGeoIpData("8.8.8.8");

        Assert.assertEquals("8.8.8.8", data.getIp());
        Assert.assertEquals("Mountain View", data.getCity());

        try {
            geoIpService.getGeoIpData("wrong IP");
            Assert.assertTrue("Wrong IP request should fail", false);
        } catch (IOException e) {
            Assert.assertTrue(e.getMessage().contains("Incorrect server response status"));
        }

    }
}