package ask.service;

import ask.geoip.GeoIpResponse;
import ask.geoip.GeoIpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created by alibin on 9/25/15.
 *
 * @author alibin
 */
@Service
public class CountryService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GeoIpService geoIpService;

    @Value("${geoip.defaultCountry}")
    private String defaultCountry;

    /**
     * Try to detect country using web service
     * If it's fail return default country
     * @param ip ip v4 address
     * @return country
     */
    public String detectCountry(String ip) {
        try {
            if ("127.0.0.1".equals(ip)) {
                return "lv"; // for test purposes
            }
            GeoIpResponse geoIpResponse = geoIpService.getGeoIpData(ip);
            String country = geoIpResponse.getCountry_code();
            if (StringUtils.hasText(country)) {
                return country.toLowerCase();
            }
        } catch (IOException e) {
            log.error("Could not detect country by IP {}. Use default value {}", ip, defaultCountry, e);
        }
        return defaultCountry.toLowerCase();
    }

}
