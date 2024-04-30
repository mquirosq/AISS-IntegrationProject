
package aiss.vimeoMiner.vimeoModel.modelVideos;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StaffPick {

    @JsonProperty("normal")
    private Boolean normal;
    @JsonProperty("best_of_the_month")
    private Boolean bestOfTheMonth;
    @JsonProperty("best_of_the_year")
    private Boolean bestOfTheYear;
    @JsonProperty("premiere")
    private Boolean premiere;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("normal")
    public Boolean getNormal() {
        return normal;
    }

    @JsonProperty("normal")
    public void setNormal(Boolean normal) {
        this.normal = normal;
    }

    @JsonProperty("best_of_the_month")
    public Boolean getBestOfTheMonth() {
        return bestOfTheMonth;
    }

    @JsonProperty("best_of_the_month")
    public void setBestOfTheMonth(Boolean bestOfTheMonth) {
        this.bestOfTheMonth = bestOfTheMonth;
    }

    @JsonProperty("best_of_the_year")
    public Boolean getBestOfTheYear() {
        return bestOfTheYear;
    }

    @JsonProperty("best_of_the_year")
    public void setBestOfTheYear(Boolean bestOfTheYear) {
        this.bestOfTheYear = bestOfTheYear;
    }

    @JsonProperty("premiere")
    public Boolean getPremiere() {
        return premiere;
    }

    @JsonProperty("premiere")
    public void setPremiere(Boolean premiere) {
        this.premiere = premiere;
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
