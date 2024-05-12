
package aiss.vimeoMiner.vimeoModel.modelUser;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LocationDetails.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("formattedAddress");
        sb.append('=');
        sb.append(((this.formattedAddress == null)?"<null>":this.formattedAddress));
        sb.append(',');
        sb.append("latitude");
        sb.append('=');
        sb.append(((this.latitude == null)?"<null>":this.latitude));
        sb.append(',');
        sb.append("longitude");
        sb.append('=');
        sb.append(((this.longitude == null)?"<null>":this.longitude));
        sb.append(',');
        sb.append("city");
        sb.append('=');
        sb.append(((this.city == null)?"<null>":this.city));
        sb.append(',');
        sb.append("state");
        sb.append('=');
        sb.append(((this.state == null)?"<null>":this.state));
        sb.append(',');
        sb.append("neighborhood");
        sb.append('=');
        sb.append(((this.neighborhood == null)?"<null>":this.neighborhood));
        sb.append(',');
        sb.append("subLocality");
        sb.append('=');
        sb.append(((this.subLocality == null)?"<null>":this.subLocality));
        sb.append(',');
        sb.append("stateIsoCode");
        sb.append('=');
        sb.append(((this.stateIsoCode == null)?"<null>":this.stateIsoCode));
        sb.append(',');
        sb.append("country");
        sb.append('=');
        sb.append(((this.country == null)?"<null>":this.country));
        sb.append(',');
        sb.append("countryIsoCode");
        sb.append('=');
        sb.append(((this.countryIsoCode == null)?"<null>":this.countryIsoCode));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
