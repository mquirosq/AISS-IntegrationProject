
package aiss.vimeoMiner.vimeoModel.modelChannel;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata__1 {

    @JsonProperty("connections")
    private Connections__1 connections;
    @JsonProperty("interactions")
    private Object interactions;
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

    @JsonProperty("interactions")
    public Object getInteractions() {
        return interactions;
    }

    @JsonProperty("interactions")
    public void setInteractions(Object interactions) {
        this.interactions = interactions;
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
