
package aiss.vimeoMiner.vimeoModel.modelVideos;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Privacy {

    @JsonProperty("view")
    private String view;
    @JsonProperty("embed")
    private String embed;
    @JsonProperty("download")
    private Boolean download;
    @JsonProperty("add")
    private Boolean add;
    @JsonProperty("comments")
    private String comments;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("view")
    public String getView() {
        return view;
    }

    @JsonProperty("view")
    public void setView(String view) {
        this.view = view;
    }

    @JsonProperty("embed")
    public String getEmbed() {
        return embed;
    }

    @JsonProperty("embed")
    public void setEmbed(String embed) {
        this.embed = embed;
    }

    @JsonProperty("download")
    public Boolean getDownload() {
        return download;
    }

    @JsonProperty("download")
    public void setDownload(Boolean download) {
        this.download = download;
    }

    @JsonProperty("add")
    public Boolean getAdd() {
        return add;
    }

    @JsonProperty("add")
    public void setAdd(Boolean add) {
        this.add = add;
    }

    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(String comments) {
        this.comments = comments;
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
