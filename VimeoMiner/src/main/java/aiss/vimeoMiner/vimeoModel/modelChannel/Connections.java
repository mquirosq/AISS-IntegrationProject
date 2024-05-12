
package aiss.vimeoMiner.vimeoModel.modelChannel;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Connections {

    @JsonProperty("albums")
    private Albums albums;
    @JsonProperty("appearances")
    private Appearances appearances;
    @JsonProperty("channels")
    private Channels channels;
    @JsonProperty("feed")
    private Feed feed;
    @JsonProperty("followers")
    private Followers followers;
    @JsonProperty("following")
    private Following following;
    @JsonProperty("groups")
    private Groups groups;
    @JsonProperty("likes")
    private Likes likes;
    @JsonProperty("membership")
    private Membership membership;
    @JsonProperty("moderated_channels")
    private ModeratedChannels moderatedChannels;
    @JsonProperty("portfolios")
    private Portfolios portfolios;
    @JsonProperty("videos")
    private Videos videos;
    @JsonProperty("shared")
    private Shared shared;
    @JsonProperty("pictures")
    private Pictures__1 pictures;
    @JsonProperty("folders_root")
    private vimeoMiner.FoldersRoot foldersRoot;
    @JsonProperty("teams")
    private Teams teams;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("albums")
    public Albums getAlbums() {
        return albums;
    }

    @JsonProperty("albums")
    public void setAlbums(Albums albums) {
        this.albums = albums;
    }

    @JsonProperty("appearances")
    public Appearances getAppearances() {
        return appearances;
    }

    @JsonProperty("appearances")
    public void setAppearances(Appearances appearances) {
        this.appearances = appearances;
    }

    @JsonProperty("channels")
    public Channels getChannels() {
        return channels;
    }

    @JsonProperty("channels")
    public void setChannels(Channels channels) {
        this.channels = channels;
    }

    @JsonProperty("feed")
    public Feed getFeed() {
        return feed;
    }

    @JsonProperty("feed")
    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    @JsonProperty("followers")
    public Followers getFollowers() {
        return followers;
    }

    @JsonProperty("followers")
    public void setFollowers(Followers followers) {
        this.followers = followers;
    }

    @JsonProperty("following")
    public Following getFollowing() {
        return following;
    }

    @JsonProperty("following")
    public void setFollowing(Following following) {
        this.following = following;
    }

    @JsonProperty("groups")
    public Groups getGroups() {
        return groups;
    }

    @JsonProperty("groups")
    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    @JsonProperty("likes")
    public Likes getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    @JsonProperty("membership")
    public Membership getMembership() {
        return membership;
    }

    @JsonProperty("membership")
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    @JsonProperty("moderated_channels")
    public ModeratedChannels getModeratedChannels() {
        return moderatedChannels;
    }

    @JsonProperty("moderated_channels")
    public void setModeratedChannels(ModeratedChannels moderatedChannels) {
        this.moderatedChannels = moderatedChannels;
    }

    @JsonProperty("portfolios")
    public Portfolios getPortfolios() {
        return portfolios;
    }

    @JsonProperty("portfolios")
    public void setPortfolios(Portfolios portfolios) {
        this.portfolios = portfolios;
    }

    @JsonProperty("videos")
    public Videos getVideos() {
        return videos;
    }

    @JsonProperty("videos")
    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    @JsonProperty("shared")
    public Shared getShared() {
        return shared;
    }

    @JsonProperty("shared")
    public void setShared(Shared shared) {
        this.shared = shared;
    }

    @JsonProperty("pictures")
    public Pictures__1 getPictures() {
        return pictures;
    }

    @JsonProperty("pictures")
    public void setPictures(Pictures__1 pictures) {
        this.pictures = pictures;
    }

    @JsonProperty("folders_root")
    public vimeoMiner.FoldersRoot getFoldersRoot() {
        return foldersRoot;
    }

    @JsonProperty("folders_root")
    public void setFoldersRoot(vimeoMiner.FoldersRoot foldersRoot) {
        this.foldersRoot = foldersRoot;
    }

    @JsonProperty("teams")
    public Teams getTeams() {
        return teams;
    }

    @JsonProperty("teams")
    public void setTeams(Teams teams) {
        this.teams = teams;
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
