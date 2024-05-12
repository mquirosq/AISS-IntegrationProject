
package aiss.vimeoMiner.vimeoModel.modelComment;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Feed {

    @JsonProperty("uri")
    private String uri;
    @JsonProperty("options")
    private List<String> options;

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

}
