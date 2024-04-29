
package aiss.vimeoMiner.vimeoModel.modelVideos;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {

    @JsonProperty("connections")
    private Connections connections;
    @JsonProperty("interactions")
    private Interactions interactions;
    @JsonProperty("is_vimeo_create")
    private Boolean isVimeoCreate;
    @JsonProperty("is_screen_record")
    private Boolean isScreenRecord;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("connections")
    public Connections getConnections() {
        return connections;
    }

    @JsonProperty("connections")
    public void setConnections(Connections connections) {
        this.connections = connections;
    }

    @JsonProperty("interactions")
    public Interactions getInteractions() {
        return interactions;
    }

    @JsonProperty("interactions")
    public void setInteractions(Interactions interactions) {
        this.interactions = interactions;
    }

    @JsonProperty("is_vimeo_create")
    public Boolean getIsVimeoCreate() {
        return isVimeoCreate;
    }

    @JsonProperty("is_vimeo_create")
    public void setIsVimeoCreate(Boolean isVimeoCreate) {
        this.isVimeoCreate = isVimeoCreate;
    }

    @JsonProperty("is_screen_record")
    public Boolean getIsScreenRecord() {
        return isScreenRecord;
    }

    @JsonProperty("is_screen_record")
    public void setIsScreenRecord(Boolean isScreenRecord) {
        this.isScreenRecord = isScreenRecord;
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
