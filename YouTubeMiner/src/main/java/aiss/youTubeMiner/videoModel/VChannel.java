package aiss.youTubeMiner.videoModel;

import java.util.List;

public class VChannel {
    private String id;
    private String name;
    private String description;
    private String createdTime;
    private List<VVideo> videos;

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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public List<VVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<VVideo> videos) {
        this.videos = videos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
