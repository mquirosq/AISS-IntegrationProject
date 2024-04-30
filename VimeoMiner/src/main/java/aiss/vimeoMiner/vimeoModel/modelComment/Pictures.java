
package aiss.vimeoMiner.vimeoModel.modelComment;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pictures {

    @JsonProperty("uri")
    private String uri;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("type")
    private String type;
    @JsonProperty("base_link")
    private String baseLink;
    @JsonProperty("sizes")
    private List<Size> sizes;
    @JsonProperty("resource_key")
    private String resourceKey;
    @JsonProperty("default_picture")
    private Boolean defaultPicture;

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
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
    public List<Size> getSizes() {
        return sizes;
    }

    @JsonProperty("sizes")
    public void setSizes(List<Size> sizes) {
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

}
