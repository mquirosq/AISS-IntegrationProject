
package aiss.vimeoMiner.vimeoModel.modelChannel;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDetails {

    @JsonProperty("formatted_address")
    private String formattedAddress;
    @JsonProperty("latitude")
    private Object latitude;
    @JsonProperty("longitude")
    private Object longitude;
    @JsonProperty("city")
    private Object city;
    @JsonProperty("state")
    private Object state;
    @JsonProperty("neighborhood")
    private Object neighborhood;
    @JsonProperty("sub_locality")
    private Object subLocality;
    @JsonProperty("state_iso_code")
    private Object stateIsoCode;
    @JsonProperty("country")
    private Object country;
    @JsonProperty("country_iso_code")
    private Object countryIsoCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("formatted_address")
    public String getFormattedAddress() {
        return formattedAddress;
    }

    @JsonProperty("formatted_address")
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    @JsonProperty("latitude")
    public Object getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Object getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("city")
    public Object getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(Object city) {
        this.city = city;
    }

    @JsonProperty("state")
    public Object getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(Object state) {
        this.state = state;
    }

    @JsonProperty("neighborhood")
    public Object getNeighborhood() {
        return neighborhood;
    }

    @JsonProperty("neighborhood")
    public void setNeighborhood(Object neighborhood) {
        this.neighborhood = neighborhood;
    }

    @JsonProperty("sub_locality")
    public Object getSubLocality() {
        return subLocality;
    }

    @JsonProperty("sub_locality")
    public void setSubLocality(Object subLocality) {
        this.subLocality = subLocality;
    }

    @JsonProperty("state_iso_code")
    public Object getStateIsoCode() {
        return stateIsoCode;
    }

    @JsonProperty("state_iso_code")
    public void setStateIsoCode(Object stateIsoCode) {
        this.stateIsoCode = stateIsoCode;
    }

    @JsonProperty("country")
    public Object getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(Object country) {
        this.country = country;
    }

    @JsonProperty("country_iso_code")
    public Object getCountryIsoCode() {
        return countryIsoCode;
    }

    @JsonProperty("country_iso_code")
    public void setCountryIsoCode(Object countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
