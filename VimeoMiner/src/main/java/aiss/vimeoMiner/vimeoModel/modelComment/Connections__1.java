
package aiss.vimeoMiner.vimeoModel.modelComment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Connections__1 {

    @JsonProperty("user")
    private User__1 user;
    @JsonProperty("replies")
    private Replies replies;

    @JsonProperty("user")
    public User__1 getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User__1 user) {
        this.user = user;
    }

    @JsonProperty("replies")
    public Replies getReplies() {
        return replies;
    }

    @JsonProperty("replies")
    public void setReplies(Replies replies) {
        this.replies = replies;
    }

}
