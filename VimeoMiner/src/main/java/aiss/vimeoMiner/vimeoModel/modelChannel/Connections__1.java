
package aiss.vimeoMiner.vimeoModel.modelChannel;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Connections__1 {

    @JsonProperty("users")
    private Users users;
    @JsonProperty("videos")
    private Videos__1 videos;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("users")
    public Users getUsers() {
        return users;
    }

    @JsonProperty("users")
    public void setUsers(Users users) {
        this.users = users;
    }

    @JsonProperty("videos")
    public Videos__1 getVideos() {
        return videos;
    }

    @JsonProperty("videos")
    public void setVideos(Videos__1 videos) {
        this.videos = videos;
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
