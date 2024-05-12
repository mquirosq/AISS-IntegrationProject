
package aiss.vimeoMiner.vimeoModel.modelVideos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {

    @JsonProperty("id")
    private String id;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("type")
    private String type;
    @JsonProperty("link")
    private String link;
    @JsonProperty("player_embed_url")
    private String playerEmbedUrl;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("width")
    private Integer width;
    @JsonProperty("language")
    private String language;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("embed")
    private Embed embed;
    @JsonProperty("created_time")
    private String createdTime;
    @JsonProperty("modified_time")
    private String modifiedTime;
    @JsonProperty("release_time")
    private String releaseTime;
    @JsonProperty("content_rating")
    private List<String> contentRating;
    @JsonProperty("content_rating_class")
    private String contentRatingClass;
    @JsonProperty("rating_mod_locked")
    private Boolean ratingModLocked;
    @JsonProperty("license")
    private Object license;
    @JsonProperty("privacy")
    private Privacy privacy;
    @JsonProperty("pictures")
    private Pictures pictures;
    @JsonProperty("tags")
    private List<Object> tags;
    @JsonProperty("stats")
    private Stats stats;
    @JsonProperty("categories")
    private List<Object> categories;
    @JsonProperty("uploader")
    private Uploader uploader;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("user")
    private User user;
    @JsonProperty("app")
    private App app;
    @JsonProperty("play")
    private Play play;
    @JsonProperty("status")
    private String status;
    @JsonProperty("resource_key")
    private String resourceKey;
    @JsonProperty("upload")
    private Object upload;
    @JsonProperty("transcode")
    private Object transcode;
    @JsonProperty("is_playable")
    private Boolean isPlayable;
    @JsonProperty("has_audio")
    private Boolean hasAudio;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("player_embed_url")
    public String getPlayerEmbedUrl() {
        return playerEmbedUrl;
    }

    @JsonProperty("player_embed_url")
    public void setPlayerEmbedUrl(String playerEmbedUrl) {
        this.playerEmbedUrl = playerEmbedUrl;
    }

    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonProperty("embed")
    public Embed getEmbed() {
        return embed;
    }

    @JsonProperty("embed")
    public void setEmbed(Embed embed) {
        this.embed = embed;
    }

    @JsonProperty("created_time")
    public String getCreatedTime() {
        return createdTime;
    }

    @JsonProperty("created_time")
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @JsonProperty("modified_time")
    public String getModifiedTime() {
        return modifiedTime;
    }

    @JsonProperty("modified_time")
    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @JsonProperty("release_time")
    public String getReleaseTime() {
        return releaseTime;
    }

    @JsonProperty("release_time")
    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    @JsonProperty("content_rating")
    public List<String> getContentRating() {
        return contentRating;
    }

    @JsonProperty("content_rating")
    public void setContentRating(List<String> contentRating) {
        this.contentRating = contentRating;
    }

    @JsonProperty("content_rating_class")
    public String getContentRatingClass() {
        return contentRatingClass;
    }

    @JsonProperty("content_rating_class")
    public void setContentRatingClass(String contentRatingClass) {
        this.contentRatingClass = contentRatingClass;
    }

    @JsonProperty("rating_mod_locked")
    public Boolean getRatingModLocked() {
        return ratingModLocked;
    }

    @JsonProperty("rating_mod_locked")
    public void setRatingModLocked(Boolean ratingModLocked) {
        this.ratingModLocked = ratingModLocked;
    }

    @JsonProperty("license")
    public Object getLicense() {
        return license;
    }

    @JsonProperty("license")
    public void setLicense(Object license) {
        this.license = license;
    }

    @JsonProperty("privacy")
    public Privacy getPrivacy() {
        return privacy;
    }

    @JsonProperty("privacy")
    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    @JsonProperty("pictures")
    public Pictures getPictures() {
        return pictures;
    }

    @JsonProperty("pictures")
    public void setPictures(Pictures pictures) {
        this.pictures = pictures;
    }

    @JsonProperty("tags")
    public List<Object> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    @JsonProperty("stats")
    public Stats getStats() {
        return stats;
    }

    @JsonProperty("stats")
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @JsonProperty("categories")
    public List<Object> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    @JsonProperty("uploader")
    public Uploader getUploader() {
        return uploader;
    }

    @JsonProperty("uploader")
    public void setUploader(Uploader uploader) {
        this.uploader = uploader;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("app")
    public App getApp() {
        return app;
    }

    @JsonProperty("app")
    public void setApp(App app) {
        this.app = app;
    }

    @JsonProperty("play")
    public Play getPlay() {
        return play;
    }

    @JsonProperty("play")
    public void setPlay(Play play) {
        this.play = play;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("resource_key")
    public String getResourceKey() {
        return resourceKey;
    }

    @JsonProperty("resource_key")
    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    @JsonProperty("upload")
    public Object getUpload() {
        return upload;
    }

    @JsonProperty("upload")
    public void setUpload(Object upload) {
        this.upload = upload;
    }

    @JsonProperty("transcode")
    public Object getTranscode() {
        return transcode;
    }

    @JsonProperty("transcode")
    public void setTranscode(Object transcode) {
        this.transcode = transcode;
    }

    @JsonProperty("is_playable")
    public Boolean getIsPlayable() {
        return isPlayable;
    }

    @JsonProperty("is_playable")
    public void setIsPlayable(Boolean isPlayable) {
        this.isPlayable = isPlayable;
    }

    @JsonProperty("has_audio")
    public Boolean getHasAudio() {
        return hasAudio;
    }

    @JsonProperty("has_audio")
    public void setHasAudio(Boolean hasAudio) {
        this.hasAudio = hasAudio;
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
