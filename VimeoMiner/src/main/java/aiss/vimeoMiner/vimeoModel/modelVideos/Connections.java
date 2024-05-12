
package aiss.vimeoMiner.vimeoModel.modelVideos;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Connections {

    @JsonProperty("comments")
    private Comments comments;
    @JsonProperty("credits")
    private Credits credits;
    @JsonProperty("likes")
    private Likes likes;
    @JsonProperty("pictures")
    private Pictures__2 pictures;
    @JsonProperty("texttracks")
    private Texttracks texttracks;
    @JsonProperty("related")
    private Object related;
    @JsonProperty("recommendations")
    private Recommendations recommendations;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("comments")
    public Comments getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(Comments comments) {
        this.comments = comments;
    }

    @JsonProperty("credits")
    public Credits getCredits() {
        return credits;
    }

    @JsonProperty("credits")
    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    @JsonProperty("likes")
    public Likes getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    @JsonProperty("pictures")
    public Pictures__2 getPictures() {
        return pictures;
    }

    @JsonProperty("pictures")
    public void setPictures(Pictures__2 pictures) {
        this.pictures = pictures;
    }

    @JsonProperty("texttracks")
    public Texttracks getTexttracks() {
        return texttracks;
    }

    @JsonProperty("texttracks")
    public void setTexttracks(Texttracks texttracks) {
        this.texttracks = texttracks;
    }

    @JsonProperty("related")
    public Object getRelated() {
        return related;
    }

    @JsonProperty("related")
    public void setRelated(Object related) {
        this.related = related;
    }

    @JsonProperty("recommendations")
    public Recommendations getRecommendations() {
        return recommendations;
    }

    @JsonProperty("recommendations")
    public void setRecommendations(Recommendations recommendations) {
        this.recommendations = recommendations;
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
