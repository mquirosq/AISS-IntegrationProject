
package aiss.vimeoMiner.vimeoModel.modelComment;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("uri")
    private String uri;
    @JsonProperty("name")
    private String name;
    @JsonProperty("link")
    private String link;
    @JsonProperty("capabilities")
    private Capabilities capabilities;
    @JsonProperty("location")
    private String location;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("short_bio")
    private String shortBio;
    @JsonProperty("created_time")
    private String createdTime;
    @JsonProperty("pictures")
    private Pictures pictures;
    @JsonProperty("websites")
    private List<Object> websites;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("location_details")
    private LocationDetails locationDetails;
    @JsonProperty("skills")
    private List<Object> skills;
    @JsonProperty("available_for_hire")
    private Boolean availableForHire;
    @JsonProperty("can_work_remotely")
    private Boolean canWorkRemotely;
    @JsonProperty("resource_key")
    private String resourceKey;
    @JsonProperty("account")
    private String account;

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

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("capabilities")
    public Capabilities getCapabilities() {
        return capabilities;
    }

    @JsonProperty("capabilities")
    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("bio")
    public String getBio() {
        return bio;
    }

    @JsonProperty("bio")
    public void setBio(String bio) {
        this.bio = bio;
    }

    @JsonProperty("short_bio")
    public String getShortBio() {
        return shortBio;
    }

    @JsonProperty("short_bio")
    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    @JsonProperty("created_time")
    public String getCreatedTime() {
        return createdTime;
    }

    @JsonProperty("created_time")
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @JsonProperty("pictures")
    public Pictures getPictures() {
        return pictures;
    }

    @JsonProperty("pictures")
    public void setPictures(Pictures pictures) {
        this.pictures = pictures;
    }

    @JsonProperty("websites")
    public List<Object> getWebsites() {
        return websites;
    }

    @JsonProperty("websites")
    public void setWebsites(List<Object> websites) {
        this.websites = websites;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("location_details")
    public LocationDetails getLocationDetails() {
        return locationDetails;
    }

    @JsonProperty("location_details")
    public void setLocationDetails(LocationDetails locationDetails) {
        this.locationDetails = locationDetails;
    }

    @JsonProperty("skills")
    public List<Object> getSkills() {
        return skills;
    }

    @JsonProperty("skills")
    public void setSkills(List<Object> skills) {
        this.skills = skills;
    }

    @JsonProperty("available_for_hire")
    public Boolean getAvailableForHire() {
        return availableForHire;
    }

    @JsonProperty("available_for_hire")
    public void setAvailableForHire(Boolean availableForHire) {
        this.availableForHire = availableForHire;
    }

    @JsonProperty("can_work_remotely")
    public Boolean getCanWorkRemotely() {
        return canWorkRemotely;
    }

    @JsonProperty("can_work_remotely")
    public void setCanWorkRemotely(Boolean canWorkRemotely) {
        this.canWorkRemotely = canWorkRemotely;
    }

    @JsonProperty("resource_key")
    public String getResourceKey() {
        return resourceKey;
    }

    @JsonProperty("resource_key")
    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    @JsonProperty("account")
    public String getAccount() {
        return account;
    }

    @JsonProperty("account")
    public void setAccount(String account) {
        this.account = account;
    }

}
