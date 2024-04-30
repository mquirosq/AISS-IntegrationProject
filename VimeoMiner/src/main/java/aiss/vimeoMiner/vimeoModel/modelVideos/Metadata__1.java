
package aiss.vimeoMiner.vimeoModel.modelVideos;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata__1 {

    @JsonProperty("connections")
    private Connections__1 connections;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("connections")
    public Connections__1 getConnections() {
        return connections;
    }

    @JsonProperty("connections")
    public void setConnections(Connections__1 connections) {
        this.connections = connections;
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
