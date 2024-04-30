
package aiss.vimeoMiner.vimeoModel.modelComment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {

    @JsonProperty("connections")
    private Connections connections;

    @JsonProperty("connections")
    public Connections getConnections() {
        return connections;
    }

    @JsonProperty("connections")
    public void setConnections(Connections connections) {
        this.connections = connections;
    }

}
