
package aiss.vimeoMiner.vimeoModel.modelComment;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User__1 {

    @JsonProperty("uri")
    private String uri;
    @JsonProperty("options")
    private List<String> options;
    @JsonProperty("name")
    private String name;
    @JsonProperty("pictures")
    private Pictures__2 pictures;
    @JsonProperty("link")
    private String link;

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("options")
    public List<String> getOptions() {
        return options;
    }

    @JsonProperty("options")
    public void setOptions(List<String> options) {
        this.options = options;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("pictures")
    public Pictures__2 getPictures() {
        return pictures;
    }

    @JsonProperty("pictures")
    public void setPictures(Pictures__2 pictures) {
        this.pictures = pictures;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

}
