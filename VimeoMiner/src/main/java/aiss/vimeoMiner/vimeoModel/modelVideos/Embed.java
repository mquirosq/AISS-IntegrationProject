
package aiss.vimeoMiner.vimeoModel.modelVideos;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Embed {

    @JsonProperty("html")
    private String html;
    @JsonProperty("badges")
    private Badges badges;
    @JsonProperty("interactive")
    private Boolean interactive;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("html")
    public String getHtml() {
        return html;
    }

    @JsonProperty("html")
    public void setHtml(String html) {
        this.html = html;
    }

    @JsonProperty("badges")
    public Badges getBadges() {
        return badges;
    }

    @JsonProperty("badges")
    public void setBadges(Badges badges) {
        this.badges = badges;
    }

    @JsonProperty("interactive")
    public Boolean getInteractive() {
        return interactive;
    }

    @JsonProperty("interactive")
    public void setInteractive(Boolean interactive) {
        this.interactive = interactive;
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
