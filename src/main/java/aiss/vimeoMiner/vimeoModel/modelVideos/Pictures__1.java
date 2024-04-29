
package aiss.vimeoMiner.vimeoModel.modelVideos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pictures__1 {

    @JsonProperty("uri")
    private Object uri;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("type")
    private String type;
    @JsonProperty("base_link")
    private String baseLink;
    @JsonProperty("sizes")
    private List<Size__1> sizes;
    @JsonProperty("resource_key")
    private String resourceKey;
    @JsonProperty("default_picture")
    private Boolean defaultPicture;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("uri")
    public Object getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(Object uri) {
        this.uri = uri;
    }

    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("base_link")
    public String getBaseLink() {
        return baseLink;
    }

    @JsonProperty("base_link")
    public void setBaseLink(String baseLink) {
        this.baseLink = baseLink;
    }

    @JsonProperty("sizes")
    public List<Size__1> getSizes() {
        return sizes;
    }

    @JsonProperty("sizes")
    public void setSizes(List<Size__1> sizes) {
        this.sizes = sizes;
    }

    @JsonProperty("resource_key")
    public String getResourceKey() {
        return resourceKey;
    }

    @JsonProperty("resource_key")
    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    @JsonProperty("default_picture")
    public Boolean getDefaultPicture() {
        return defaultPicture;
    }

    @JsonProperty("default_picture")
    public void setDefaultPicture(Boolean defaultPicture) {
        this.defaultPicture = defaultPicture;
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
