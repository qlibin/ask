package ask.geoip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by alibin on 9/25/15.
 *
 * @author alibin
 */
@Service
public class GeoIpService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Value("${geoip.telize.url}")
    private String geoIpServiceUrl;

    private volatile RestTemplate restTemplate;

    @Cacheable(value = "getGeoIpData")
    public GeoIpResponse getGeoIpData(String ip) throws IOException {
        ResponseEntity<GeoIpResponse> response =
                getRestTemplate().exchange(
                        geoIpServiceUrl,
                        HttpMethod.GET,
                        null,
                        GeoIpResponse.class,
                        ip
                );
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IOException("Incorrect server response status " + response.getStatusCode());
        }
        return response.getBody();
    }

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            synchronized (this) {
                if (restTemplate == null) {
                    restTemplate = new RestTemplate();
                    if (ClassUtils.isPresent("org.apache.http.client.config.RequestConfig", null)) {
                        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
                    }
                    restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
                        @Override
                        public void handleError(ClientHttpResponse response) throws IOException {
                            log.error("HTTP status code: " + response.getStatusCode());
                            HttpStatus statusCode = response.getStatusCode();
                            switch (statusCode.series()) {
                                case CLIENT_ERROR:
                                case SERVER_ERROR:
                                    return;
                                default:
                                    throw new RestClientException("Unknown status code [" + statusCode + "]");
                            }
                        }
                    });
                }
            }
        }
        return restTemplate;
    }

}
