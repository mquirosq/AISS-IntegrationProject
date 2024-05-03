package aiss.vimeoMiner.videoModel;

import java.util.List;

public class VVideo {
    private String id;
    private String name;
    private String description;
    private String releaseTime;
    private List<VComment> comments;
    private List<VCaption> captions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public List<VComment> getComments() {
        return comments;
    }

    public void setComments(List<VComment> comments) {
        this.comments = comments;
    }

    public List<VCaption> getCaptions() {
        return captions;
    }

    public void setCaptions(List<VCaption> captions) {
        this.captions = captions;
    }
}
