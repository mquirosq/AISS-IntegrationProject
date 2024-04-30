
package aiss.vimeoMiner.vimeoModel.modelVideos;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Badges {

    @JsonProperty("hdr")
    private Boolean hdr;
    @JsonProperty("live")
    private Live live;
    @JsonProperty("staff_pick")
    private StaffPick staffPick;
    @JsonProperty("vod")
    private Boolean vod;
    @JsonProperty("weekend_challenge")
    private Boolean weekendChallenge;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("hdr")
    public Boolean getHdr() {
        return hdr;
    }

    @JsonProperty("hdr")
    public void setHdr(Boolean hdr) {
        this.hdr = hdr;
    }

    @JsonProperty("live")
    public Live getLive() {
        return live;
    }

    @JsonProperty("live")
    public void setLive(Live live) {
        this.live = live;
    }

    @JsonProperty("staff_pick")
    public StaffPick getStaffPick() {
        return staffPick;
    }

    @JsonProperty("staff_pick")
    public void setStaffPick(StaffPick staffPick) {
        this.staffPick = staffPick;
    }

    @JsonProperty("vod")
    public Boolean getVod() {
        return vod;
    }

    @JsonProperty("vod")
    public void setVod(Boolean vod) {
        this.vod = vod;
    }

    @JsonProperty("weekend_challenge")
    public Boolean getWeekendChallenge() {
        return weekendChallenge;
    }

    @JsonProperty("weekend_challenge")
    public void setWeekendChallenge(Boolean weekendChallenge) {
        this.weekendChallenge = weekendChallenge;
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
