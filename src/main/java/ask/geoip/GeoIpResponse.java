package ask.geoip;

import com.google.common.base.Objects;

/**
 * Created by alibin on 9/25/15.
 *
 * @author alibin
 */
public class GeoIpResponse {

    String ip; // (Visitor IP address, or IP address specified as parameter)
    String country_code; // (Two-letter ISO 3166-1 alpha-2 country code)
    String country_code3; // (Three-letter ISO 3166-1 alpha-3 country code)
    String country; // (Name of the country)
    String region_code; // (Two-letter ISO-3166-2 state / region code)
    String region; // (Name of the region)
    String city; // (Name of the city)
    String postal_code; // (Postal code / Zip code)
    String continent_code; // (Two-letter continent code)
    Double latitude; // (Latitude)
    Double longitude; // (Longitude)
    String dma_code; // (DMA Code)
    String area_code; // (Area Code)
    String asn; // (Autonomous System Number)
    String isp; // (Internet service provider)
    String timezone; // (Time Zone)

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_code3() {
        return country_code3;
    }

    public void setCountry_code3(String country_code3) {
        this.country_code3 = country_code3;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getContinent_code() {
        return continent_code;
    }

    public void setContinent_code(String continent_code) {
        this.continent_code = continent_code;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDma_code() {
        return dma_code;
    }

    public void setDma_code(String dma_code) {
        this.dma_code = dma_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("ip", ip)
                .add("country_code", country_code)
                .add("country", country)
                .add("city", city)
                .toString();
    }
}
